package com.aivox.common_ui.group;

import android.content.Context;
import com.aivox.base.databinding.OnItemClickListener;
import com.aivox.base.databinding.OnNullViewItemClickListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class GroupRecyclerAdapter<Parent, Child> extends BaseRecyclerAdapter<Child> {
    private List<Parent> mGroupTitles;
    private LinkedHashMap<Parent, List<Child>> mGroups;
    public OnItemClickListener mOnItemClickListener;
    public OnNullViewItemClickListener onNullViewItemClickListener;

    public GroupRecyclerAdapter(Context context) {
        super(context);
        this.mGroups = new LinkedHashMap<>();
        this.mGroupTitles = new ArrayList();
    }

    Parent getGroup(int i) {
        return this.mGroupTitles.get(i);
    }

    int getGroupCount() {
        return this.mGroupTitles.size();
    }

    int getChildCount(int i) {
        if (this.mGroupTitles == null || this.mGroups.size() == 0 || this.mGroups.get(this.mGroupTitles.get(i)) == null) {
            return 0;
        }
        return this.mGroups.get(this.mGroupTitles.get(i)).size();
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    protected void resetGroups(LinkedHashMap<Parent, List<Child>> linkedHashMap, List<Parent> list) {
        if (linkedHashMap == null || list == null) {
            return;
        }
        this.mGroups.clear();
        this.mGroupTitles.clear();
        this.mGroups.putAll(linkedHashMap);
        this.mGroupTitles.addAll(list);
        this.mItems.clear();
        Iterator<Parent> it = this.mGroups.keySet().iterator();
        while (it.hasNext()) {
            this.mItems.addAll(this.mGroups.get(it.next()));
        }
        notifyDataSetChanged();
    }

    public final void clearGroup() {
        this.mGroupTitles.clear();
        this.mGroups.clear();
        clear();
    }

    public boolean removeGroupItem(int i) {
        int groupIndex = getGroupIndex(i);
        removeGroupChildren(groupIndex);
        int childCount = getChildCount(groupIndex);
        removeItem(i);
        if (childCount > 0) {
            return false;
        }
        this.mGroupTitles.remove(groupIndex);
        return true;
    }

    private int getGroupIndex(int i) {
        if (i <= 0) {
            return 0;
        }
        Iterator<Parent> it = this.mGroups.keySet().iterator();
        int size = 0;
        int i2 = 0;
        while (it.hasNext()) {
            size += this.mGroups.get(it.next()).size();
            if (i < size) {
                return i2;
            }
            i2++;
        }
        return 0;
    }

    private void removeGroupChildren(int i) {
        List<Child> list;
        if (i >= this.mGroupTitles.size() || (list = this.mGroups.get(this.mGroupTitles.get(i))) == null || list.size() == 0) {
            return;
        }
        list.remove(list.size() - 1);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnNullViewItemClickListener(OnNullViewItemClickListener onNullViewItemClickListener) {
        this.onNullViewItemClickListener = onNullViewItemClickListener;
    }
}
