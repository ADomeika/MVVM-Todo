package dev.domeika.todo.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.domeika.todo.R;
import dev.domeika.todo.database.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> mTodos;
    private OnTodoClickListener mOnTodoClickListener;
    private OnCheckBoxClickListener mOnCheckBoxClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private CheckBox cbIsComplete;
        OnTodoClickListener onTodoClickListener;
        OnCheckBoxClickListener onCheckBoxClickListener;

        ViewHolder(View itemView, OnTodoClickListener onTodoClickListener, OnCheckBoxClickListener onCheckBoxClickListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTodoTitle);
            cbIsComplete = itemView.findViewById(R.id.cbIsComplete);

            this.onTodoClickListener = onTodoClickListener;
            this.onCheckBoxClickListener = onCheckBoxClickListener;

            cbIsComplete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cbIsComplete:
                    onCheckBoxClickListener.onCheckBoxClick(getAdapterPosition());
                    break;
                case R.id.todo_list_item:
                    onTodoClickListener.onTodoClick(getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public TodoAdapter(List<Todo> todos, OnTodoClickListener onTodoClickListener, OnCheckBoxClickListener onCheckBoxClickListener) {
        mTodos = todos;
        this.mOnTodoClickListener = onTodoClickListener;
        this.mOnCheckBoxClickListener = onCheckBoxClickListener;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list_item, parent, false);

        return new ViewHolder(view, mOnTodoClickListener, mOnCheckBoxClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Todo todo = mTodos.get(position);
        holder.textView.setText(todo.getTitle());
        holder.cbIsComplete.setChecked(todo.getIsComplete());
        if (todo.getIsComplete()) {
            holder.textView.setTextColor(Color.parseColor("#777777"));
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    public interface OnTodoClickListener {
        void onTodoClick(int position);
    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(int position);
    }
}
