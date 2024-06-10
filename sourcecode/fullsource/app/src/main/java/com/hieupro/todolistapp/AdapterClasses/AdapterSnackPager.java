package com.hieupro.todolistapp.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import com.hieupro.todolistapp.Listeners.ColorClickListeners;
import com.hieupro.todolistapp.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AdapterSnackPager extends PagerAdapter {
    private List<String> categories;
    private Map<String, ArrayList<Integer>> categoryImageMap;
    private ColorClickListeners colorClickListener;
    private Context context;

    @Override 
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public AdapterSnackPager(Context context, List<String> list, Map<String, ArrayList<Integer>> map, ColorClickListeners colorClickListeners) {
        this.context = context;
        this.categories = list;
        this.categoryImageMap = map;
        this.colorClickListener = colorClickListeners;
    }

    @Override 
    public int getCount() {
        return this.categories.size();
    }

    @Override 
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        ArrayList<Integer> arrayList = this.categoryImageMap.get(this.categories.get(i));
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.snack_pager_items, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.snack_recyclerView);
        recyclerView.setAdapter(new AdapterSnack(arrayList, this.context, this.colorClickListener));
        recyclerView.setLayoutManager(new GridLayoutManager(this.context, 4));
        viewGroup.addView(inflate);
        return inflate;
    }

    @Override 
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override 
    public CharSequence getPageTitle(int i) {
        return this.categories.get(i);
    }
}
