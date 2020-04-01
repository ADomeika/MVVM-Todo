package dev.domeika.todo.adapter;

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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private CheckBox cbIsComplete;
        OnTodoClickListener onTodoClickListener;

        ViewHolder(View itemView, OnTodoClickListener onTodoClickListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTodoTitle);
            cbIsComplete = itemView.findViewById(R.id.cbIsComplete);

            this.onTodoClickListener = onTodoClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTodoClickListener.onTodoClick(getAdapterPosition());
        }
    }

    public TodoAdapter(List<Todo> todos, OnTodoClickListener onTodoClickListener) {
        mTodos = todos;
        this.mOnTodoClickListener = onTodoClickListener;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list_item, parent, false);

        return new ViewHolder(view, mOnTodoClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Todo todo = mTodos.get(position);
        holder.textView.setText(todo.getTitle());
        holder.cbIsComplete.setChecked(todo.getIsComplete());
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    public interface OnTodoClickListener {
        void onTodoClick(int position);
    }
}
