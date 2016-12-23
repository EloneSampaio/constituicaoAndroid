package com.forksystem.constituicao.adapter;

/**
 * Created by sam on 05/12/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.forksystem.constituicao.R;


/**
 * Created by elanicdroid on 14/09/15.
 */
public class LoaderViewHolder extends RecyclerView.ViewHolder {

    ProgressBar mProgressBar;


    public LoaderViewHolder(View itemView) {
        super(itemView);
        mProgressBar=(ProgressBar) itemView.findViewById(R.id.progressbarLoadMore);
      }
}
