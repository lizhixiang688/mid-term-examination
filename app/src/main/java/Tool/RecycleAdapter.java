package Tool;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timecube.Note;
import com.example.timecube.R;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    private List<Note> list;
    private LongClickListener longClickListener;

    public RecycleAdapter(List<Note>list,LongClickListener listener){
        this.list=list;
        this.longClickListener=listener;
    }
    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.listview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    longClickListener.OnLongClick(v,viewHolder.getAdapterPosition());

                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        Note note=list.get(position);
        holder.txtnote.setText(note.getNote());
        holder.txtremind.setText(format.format(note.getRemind_time()));
        holder.txtdeadline.setText(format.format(note.getDeadline()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View listview;
        TextView txtnote,txtdeadline,txtremind;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listview=itemView;
           txtnote=(TextView)itemView.findViewById(R.id.txt_note);
           txtdeadline=(TextView)itemView.findViewById(R.id.txt_deadine);
           txtremind=(TextView)itemView.findViewById(R.id.txt_remind);
        }
    }
}
