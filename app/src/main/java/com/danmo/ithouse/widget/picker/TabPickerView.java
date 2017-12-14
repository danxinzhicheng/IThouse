package com.danmo.ithouse.widget.picker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.danmo.commonutil.SharedPreferencesHelper;
import com.danmo.commonutil.TDevice;
import com.danmo.ithouse.BuildConfig;
import com.danmo.ithouse.R;
import com.danmo.ithouse.base.BaseApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 自定义分类栏目
 * <p>
 * 动态栏目View 请通过{@link #setTabPickerManager(TabPickerDataManager)}来设置活动数据和原始数据，数据
 * 对象根据需要实现{@link Object#hashCode()}和{@link Object#equals(Object)}方法，因为非活动数据是通过使用
 * {@link List#contains(Object)}方法从原始数据剔除活动数据实现的。
 * <p>
 * <p>活动动态栏目的添加、删除、移动、选择通过{@link OnTabPickingListener}来实现的，你可以通过方法
 * {@link #setOnTabPickingListener(OnTabPickingListener)}来监听。
 * <p>
 * <p>通过{@link #show(int)}和{@link #hide()}方法来显示隐藏动态栏目界面。
 * <p>
 * <p>通过{@link #onTurnBack()}响应回退事件。
 * <p>
 */
@SuppressWarnings("all")
public class TabPickerView extends FrameLayout {

    private View mRootView;
    private TextView mViewOperator;
    private RecyclerView mRecyclerActive;
    private RecyclerView mRecyclerInactive;
    private ItemTouchHelper mItemTouchHelper;

    private TabAdapter<TabAdapter.ViewHolder> mActiveAdapter;
    private TabAdapter<TabAdapter.ViewHolder> mInactiveAdapter;

    private TabPickerDataManager mTabManager;
    private OnTabPickingListener mTabPickingListener;

    private Action1<ViewPropertyAnimator> mOnShowAnimator;
    private Action1<ViewPropertyAnimator> mOnHideAnimator;

    private int mSelectedIndex = 0;

    public TabPickerView(Context context) {
        this(context, null);
    }

    public TabPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidgets();
    }

    /**
     * The Tab Picking Listener Interface
     */
    public interface OnTabPickingListener {
        /**
         * 单击选择某个tab
         *
         * @param position select a tab
         */
        void onSelected(int position);

        /**
         * 删除某个tab
         *
         * @param position the moved tab's position
         * @param tab      the moved tab
         */
        void onRemove(int position, SubTab tab);

        /**
         * 添加某个tab
         *
         * @param tab the inserted tab
         */
        void onInsert(SubTab tab);

        /**
         * 交换tab
         *
         * @param op      the mover's position
         * @param mover   the moving tab
         * @param np      the swapper's position
         * @param swapper the swapped tab
         */
        void onMove(int op, int np);

        /**
         * 重新持久化活动的tabs
         *
         * @param activeTabs the actived tabs
         */
        void onRestore(List<SubTab> activeTabs);
    }

    public interface Action1<T> {
        void call(T t);
    }

    private void initWidgets() {
        mRootView = LayoutInflater.from(getContext())
                .inflate(R.layout.view_tab_picker, this, false);
        mRootView.setClickable(true);
        mRecyclerActive = (RecyclerView) mRootView.findViewById(R.id.view_recycler_active);
        mRecyclerInactive = (RecyclerView) mRootView.findViewById(R.id.view_recycler_inactive);
        mViewOperator = (TextView) mRootView.findViewById(R.id.tv_operator);
        addView(mRootView);
    }

    public void setOnShowAnimation(Action1<ViewPropertyAnimator> l) {
        this.mOnShowAnimator = l;
    }

    public void setOnHideAnimator(Action1<ViewPropertyAnimator> l) {
        this.mOnHideAnimator = l;
    }

    public void show(int selectedIndex) {
        final int tempIndex = mSelectedIndex;
        mSelectedIndex = selectedIndex;
        mActiveAdapter.notifyItemChanged(tempIndex);
        mActiveAdapter.notifyItemChanged(mSelectedIndex);

        if (mOnShowAnimator != null) mOnShowAnimator.call(null);
        setVisibility(VISIBLE);
        mViewOperator.setAlpha(0);
        mViewOperator.animate().alpha(1).setDuration(380).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mViewOperator.setAlpha(1);
            }
        });
        mRootView.setTranslationY(-mRootView.getHeight());
        mRootView.animate().translationY(0).setDuration(380).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRootView.setTranslationY(0);
            }
        });
    }

    public void hide() {
        if (mTabPickingListener != null) {
            mTabPickingListener.onRestore(mTabManager.mActiveDataSet);
            mTabPickingListener.onSelected(mSelectedIndex);
        }

        if (mOnHideAnimator != null) mOnHideAnimator.call(null);
        mViewOperator.animate().alpha(0).setDuration(380).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(GONE);
            }
        });
        mRootView.animate().translationY(-mRootView.getHeight()).setDuration(380);
    }

    private void initRecyclerView() {
        if (mRecyclerActive.getAdapter() != null && mRecyclerInactive.getAdapter() != null) return;

        /* - set up Active Recycler - */
        mActiveAdapter = new TabAdapter<TabAdapter.ViewHolder>(mTabManager.mActiveDataSet) {
            @Override
            public TabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new TabAdapter.ViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.view_tab_item, parent, false));
            }

            @Override
            public void onBindViewHolder(TabAdapter.ViewHolder holder, int position) {
                SubTab item = items.get(position);
                holder.mViewTab.setText(item.getName());
                if (item.isFixed()) {
                    holder.mViewTab.setActivated(false);
                } else {
                    holder.mViewTab.setActivated(true);
                }
                if (mSelectedIndex == position) {
                    holder.mViewTab.setSelected(true);
                } else {
                    holder.mViewTab.setSelected(false);
                }
                if (!TextUtils.isEmpty(item.getTag())) {
                    holder.mViewBubble.setText(item.getTag());
                    holder.mViewBubble.setVisibility(VISIBLE);
                } else {
                    holder.mViewBubble.setVisibility(GONE);
                }
                if (isEditMode() && !item.isFixed()) {
                    holder.mViewDel.setVisibility(VISIBLE);
                } else {
                    holder.mViewDel.setVisibility(GONE);
                }
                if (mBindViewObserver != null) {
                    mBindViewObserver.call(holder);
                }
            }

            @Override
            public int getItemCount() {
                return items.size();
            }

        };

        mActiveAdapter.setOnClickItemListener(new Action1<Integer>() {
            @Override
            public void call(Integer position) {
                // 先调用隐藏, 因为隐藏有保存,更新tab的动作
                int tempIndex = mSelectedIndex;
                mSelectedIndex = position;
                mActiveAdapter.notifyItemChanged(tempIndex);
                mActiveAdapter.notifyItemChanged(mSelectedIndex);
                hide();
            }
        });

        mActiveAdapter.setOnDeleteItemListener(new Action1<Integer>() {
            @Override
            public void call(Integer position) {
                SubTab tab = mActiveAdapter.getItem(position);
                int oldCount = mActiveAdapter.getItemCount();
                tab = mActiveAdapter.removeItem(position);

                int i = 0;
                for (; i < mTabManager.mInactiveDataSet.size(); i++) {
                    SubTab item = mTabManager.mInactiveDataSet.get(i);
                    break;
                }
                mTabManager.mInactiveDataSet.add(i, tab);
                mInactiveAdapter.notifyItemInserted(i);

                if (mSelectedIndex == position) {
                    mSelectedIndex = position == oldCount - 1 ? mSelectedIndex - 1 : mSelectedIndex;
                    mActiveAdapter.notifyItemChanged(mSelectedIndex);
                } else if (mSelectedIndex > position) {
                    --mSelectedIndex;
                    mActiveAdapter.notifyItemChanged(mSelectedIndex);
                }
                if (mTabPickingListener != null) {
                    mTabPickingListener.onRemove(position, tab);
                }
            }
        });
        mRecyclerActive.setAdapter(mActiveAdapter);

        mItemTouchHelper = new ItemTouchHelper(mActiveAdapter.newItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mRecyclerActive);
        mRecyclerActive.setLayoutManager(new GridLayoutManager(getContext(), 4));

        /* - set up Inactive Recycler - */
        mRecyclerInactive.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mInactiveAdapter = new TabAdapter<TabAdapter.ViewHolder>(mTabManager.mInactiveDataSet) {
            @Override
            public TabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new TabAdapter.ViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.view_tab_item, parent, false));
            }

            @Override
            public void onBindViewHolder(TabAdapter.ViewHolder holder, int position) {
                holder.mViewTab.setText(items.get(position).getName());
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        };
        mInactiveAdapter.setOnClickItemListener(new Action1<Integer>() {
            @Override
            public void call(Integer position) {
                // fix double click会out of array
                if (position < 0 || position >= mInactiveAdapter.getItemCount()) return;
                SubTab tab = mInactiveAdapter.removeItem(position);
                mActiveAdapter.addItem(tab);
                if (mTabPickingListener != null) {
                    mTabPickingListener.onInsert(tab);
                }
            }
        });
        mRecyclerInactive.setAdapter(mInactiveAdapter);
    }


    public void setTabPickerManager(TabPickerDataManager manager) {
        if (manager == null) return;
        mTabManager = manager;
        initRecyclerView();
    }

    public TabPickerDataManager getTabPickerManager() {
        return mTabManager;
    }

    public void setOnTabPickingListener(OnTabPickingListener l) {
        mTabPickingListener = l;
    }

    public boolean onTurnBack() {
        if (mActiveAdapter.isEditMode()) {
            mActiveAdapter.cancelEditMode();
            return true;
        }
        if (getVisibility() == VISIBLE) {
            hide();
            return true;
        }
        return false;
    }

    /**
     * Class TabAdapter
     *
     * @param <VH>
     */
    private abstract class TabAdapter<VH extends RecyclerView.ViewHolder>
            extends RecyclerView.Adapter<VH> {

        private OnClickListener mClickDeleteListener;
        private OnClickListener mClickTabItemListener;
        private OnTouchListener mTouchTabItemListener;

        private Action1<Integer> mDeleteItemAction;
        private Action1<Integer> mSelectItemAction;
        Action1<ViewHolder> mBindViewObserver;

        private boolean isEditMode = false;
        List<SubTab> items;

        TabAdapter(List<SubTab> items) {
            this.items = items;
        }

        SubTab removeItem(int position) {
            SubTab b = items.remove(position);
            notifyItemRemoved(position);
            return b;
        }

        void addItem(SubTab bean) {
            items.add(bean);
            notifyItemInserted(items.size() - 1);
        }

        void addItem(SubTab bean, int index) {
            items.add(index, bean);
            notifyItemInserted(index);
        }

        SubTab getItem(int position) {
            if (position < 0 || position >= items.size()) return null;
            return items.get(position);
        }

        void startEditMode() {
            mViewOperator.setText("拖动排序，返回键取消");
            isEditMode = true;
            notifyDataSetChanged();
        }

        void cancelEditMode() {
            mViewOperator.setText("长按切换栏目");
            isEditMode = false;
            notifyDataSetChanged();
        }

        boolean isEditMode() {
            return isEditMode;
        }

        void registerBindViewObserver(Action1<ViewHolder> l) {
            this.mBindViewObserver = l;
        }

        void unRegisterBindViewObserver() {
            this.mBindViewObserver = null;
        }

        OnClickListener getClickTabItemListener() {
            if (mClickTabItemListener == null) {
                mClickTabItemListener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewHolder holder = (ViewHolder) v.getTag();
                        if (holder == null) return;
                        if (mSelectItemAction != null) {
                            mSelectItemAction.call(holder.getAdapterPosition());
                        }
                    }
                };
            }
            return mClickTabItemListener;
        }

        OnClickListener getDeleteItemListener() {
            if (mClickDeleteListener == null) {
                mClickDeleteListener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewHolder holder = (ViewHolder) v.getTag();
                        if (holder == null) return;
                        if (mDeleteItemAction != null) {
                            mDeleteItemAction.call(holder.getAdapterPosition());
                        }
                    }
                };
            }
            return mClickDeleteListener;
        }

        OnTouchListener getTouchTabItemListener() {
            if (mTouchTabItemListener == null) {
                mTouchTabItemListener = new OnTabTouchListener();
            }
            return mTouchTabItemListener;
        }

        void setOnClickItemListener(Action1<Integer> l) {
            this.mSelectItemAction = l;
        }

        void setOnDeleteItemListener(Action1<Integer> l) {
            this.mDeleteItemAction = l;
        }

        TabItemTouchHelperCallback newItemTouchHelperCallback() {
            return new TabItemTouchHelperCallback();
        }

        /**
         * Tab View Holder
         */
        class ViewHolder extends RecyclerView.ViewHolder {

            TextView mViewTab;
            TextView mViewBubble;
            ImageView mViewDel;

            ViewHolder(View view) {
                super(view);
                mViewTab = (TextView) view.findViewById(R.id.tv_content);
                mViewBubble = (TextView) view.findViewById(R.id.tv_bubble);
                mViewDel = (ImageView) view.findViewById(R.id.iv_delete);

                mViewTab.setTextColor(new ColorStateList(new int[][]{
                                new int[]{-android.R.attr.state_activated},
                                new int[]{}
                        }, new int[]{0XFF24CF5F, 0XFF6A6A6A})
                );
                mViewTab.setActivated(true);

                mViewTab.setTag(this);
                mViewDel.setTag(this);
                mViewDel.setOnClickListener(getDeleteItemListener());
                mViewTab.setOnClickListener(getClickTabItemListener());
                mViewTab.setOnTouchListener(getTouchTabItemListener());
            }
        }

        /**
         * Inner Tab Touch Listener
         */
        private class OnTabTouchListener implements OnTouchListener {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewHolder holder = (ViewHolder) v.getTag();
                if (holder == null) return false;
                if (isEditMode() && MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mItemTouchHelper.startDrag(holder);
                    return true;
                }
                return false;
            }
        }

        class TabItemTouchHelperCallback extends ItemTouchHelper.Callback {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlag = 0;
                int position = viewHolder.getAdapterPosition();
                if (position > 0 && position < items.size()) {
                    if (!items.get(position).isFixed()) {
                        dragFlag = ItemTouchHelper.UP
                                | ItemTouchHelper.DOWN
                                | ItemTouchHelper.LEFT
                                | ItemTouchHelper.RIGHT;
                    }
                }
                return makeMovementFlags(dragFlag, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                int fromTargetIndex = viewHolder.getAdapterPosition();
                int toTargetIndex = target.getAdapterPosition();

                if (fromTargetIndex == toTargetIndex) return true;
                if (items.get(toTargetIndex).isFixed()) return true;

                SubTab tab = items.remove(fromTargetIndex);
                items.add(toTargetIndex, tab);

                if (mSelectedIndex == fromTargetIndex) {
                    mSelectedIndex = toTargetIndex;
                } else if (mSelectedIndex == toTargetIndex) {
                    mSelectedIndex = fromTargetIndex > toTargetIndex ? mSelectedIndex + 1 : mSelectedIndex - 1;
                } else if (toTargetIndex <= mSelectedIndex && mSelectedIndex < fromTargetIndex) {
                    ++mSelectedIndex;
                } else if (fromTargetIndex < mSelectedIndex && mSelectedIndex < toTargetIndex) {
                    --mSelectedIndex;
                }

                notifyItemMoved(fromTargetIndex, toTargetIndex);
                if (mTabPickingListener != null) {
                    mTabPickingListener.onMove(fromTargetIndex, toTargetIndex);
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // pass
            }

            @Override
            public void onSelectedChanged(final RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder == null) return;
                ((ViewHolder) viewHolder).mViewTab.setSelected(true);
                if (isEditMode()) return;
                final int position = viewHolder.getAdapterPosition();

                // onBindViewHolder之后，ViewHolder.itemView.getParent() != RecycleView
                // 估计是在onBindViewHolder之后绑定了ViewParent的，延迟500，暂时没什么好办法
                registerBindViewObserver(new Action1<ViewHolder>() {
                    @Override
                    public void call(final ViewHolder viewHolder) {
                        int index = viewHolder.getAdapterPosition();
                        if (index != position) return;
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mItemTouchHelper.startDrag(viewHolder);
                            }
                        }, 500);
                        unRegisterBindViewObserver();
                    }
                });
                startEditMode();
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                if (mSelectedIndex == viewHolder.getAdapterPosition()) return;
                ((ViewHolder) viewHolder).mViewTab.setSelected(false);
            }
        }

    }

    /**
     * Tab Data Picker Manager, Manager the Tab Data's behavior
     *
     * @param
     */
    public abstract static class TabPickerDataManager {

        public List<SubTab> mActiveDataSet;
        public List<SubTab> mInactiveDataSet;
        public List<SubTab> mOriginalDataSet;

        public TabPickerDataManager() {
            mActiveDataSet = setupActiveDataSet();
            mOriginalDataSet = setupOriginalDataSet();
            mInactiveDataSet = new ArrayList<>();

            if (mOriginalDataSet == null || mOriginalDataSet.size() == 0) {
                throw new RuntimeException("Original Data Set can't be null or empty");
            }

            if (mActiveDataSet == null) {
                mActiveDataSet = new ArrayList<>();
                for (SubTab item : mOriginalDataSet) {
                    if (item.isActived() || item.isFixed()) {
                        mActiveDataSet.add(item);
                    }
                }
                restoreActiveDataSet(mActiveDataSet);
            } else if (isUpdate(BaseApplication.sAppContext)) {
                List<SubTab> mActiveList = new ArrayList<>();
                // 替换老列表项
                for (SubTab item : mActiveDataSet) {
                    int position = mOriginalDataSet.indexOf(item);
                    if (position == -1) continue;
                    mActiveList.add(mOriginalDataSet.get(position));
                }

                // 将未加入的新项加入活动列表
                for (SubTab item : mOriginalDataSet) {
                    if (item.isActived() && !mActiveList.contains(item)) {
                        mActiveList.add(item);
                    }
                }

                mActiveDataSet = mActiveList;
                mActiveList = null;
                restoreActiveDataSet(mActiveDataSet);
            }

            Collections.sort(mActiveDataSet, new Comparator<SubTab>() {
                @Override
                public int compare(SubTab o1, SubTab o2) {
                    if (o1.isFixed() && !o2.isFixed()) return -1;
                    if (!o1.isFixed() && o2.isFixed()) return 1;
                    return 0;
                }
            });

            for (SubTab item : mOriginalDataSet) {
                if (mActiveDataSet.contains(item)) continue;
                mInactiveDataSet.add(item);
            }
        }

        public static boolean isUpdate(Context context) {
            int mVersionCode = TDevice.getVersionCode(context);
            Object mask = SharedPreferencesHelper.get(context, "TabsMask", -1);
            if (BuildConfig.DEBUG) return false;
//            return mVersionCode != (int) mask;
            return false;
        }

        public List<SubTab> getActiveDataSet() {
            return mActiveDataSet;
        }

        public List<SubTab> getInActiveDataSet() {
            return mInactiveDataSet;
        }

        public List<SubTab> getOriginalDataSet() {
            return mOriginalDataSet;
        }

        public abstract List<SubTab> setupActiveDataSet();

        public abstract List<SubTab> setupOriginalDataSet();

        public abstract void restoreActiveDataSet(List<SubTab> mActiveDataSet);
    }

}
