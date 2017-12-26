package com.example.mygpuimage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;

import com.example.library.GPUImageContrastFilter;
import com.example.library.GPUImageFilter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 123 on 2017/12/25.
 */

public class GPUImageFilterTools {

    public static void showDialog(final Context context,final OnGpuImageFilterChosenListener listener){
        final FilterList filters = new FilterList();
        filters.addFilter("Contrast",FilterType.CONTRAST);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose a filter");
        builder.setItems(filters.names.toArray(new String[filters.names.size()]),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int item) {
                        listener.onGpuImageFilterChosenListener(
                                createFilterForType(context, filters.filters.get(item)));
                    }
                });
        builder.create().show();
    }

    private static GPUImageFilter createFilterForType(Context context,FilterType type){
        switch (type){
            case CONTRAST:
                return new GPUImageContrastFilter(2.0f);
            default:
                throw new IllegalStateException("No filter of what type");
        }
    }

    public interface OnGpuImageFilterChosenListener{
        void onGpuImageFilterChosenListener(GPUImageFilter filter);
    }

    private enum FilterType{
        CONTRAST
    }

    private static class FilterList{
        public List<String> names = new LinkedList<String>();
        public List<FilterType> filters = new LinkedList<FilterType>();

        public void addFilter(String name,FilterType filter){
            names.add(name);
            filters.add(filter);
        }
    }

    public static class FilterAdjuster{

        private final Adjuster<? extends GPUImageFilter> adjuster;

        public FilterAdjuster(final GPUImageFilter filter){
            if (filter instanceof GPUImageContrastFilter)
            {
                adjuster = new ContrastAdjuster().filter(filter);
            }
            else {
                adjuster = null;
            }
        }

        public boolean canAdjust() {
            return adjuster != null;
        }

        public void adjust(final int percentage) {
            if (adjuster != null) {
                adjuster.adjust(percentage);
            }
        }

        private abstract class Adjuster<T extends GPUImageFilter>{
            private T filter;

            @SuppressWarnings("unchecked")
            public Adjuster<T> filter(final GPUImageFilter filter){
                this.filter = (T) filter;
                return this;
            }

            public T getFilter(){
                return filter;
            }

            public abstract void adjust(int percentage);

            protected float range(final int percentage,final float start,final float end){
                return (end - start) * percentage / 100.0f + start;
            }

            protected int range(final int percentage,final int start,final int end){
                return (end - start) * percentage / 100 + start;
            }
        }

        private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setContrast(range(percentage, 0.0f, 2.0f));
            }
        }
    }


}
