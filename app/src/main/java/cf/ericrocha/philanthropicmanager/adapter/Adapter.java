package cf.ericrocha.philanthropicmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.R;
import cf.ericrocha.philanthropicmanager.model.CalenderModel;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private List<CalenderModel> listCalenderModel;

    public Adapter(List<CalenderModel> list) {
        this.listCalenderModel = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CalenderModel calenderModel = listCalenderModel.get( position );
        holder.title.setText( calenderModel.getTitle() );
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        holder.date.setText( sdf.format(calenderModel.getDate()));
        holder.extra.setText( calenderModel.getExtra() );
        if(calenderModel.getCor().equals(0)){
            holder.evento.setBackgroundResource(R.color.colorWork);

        }else{
            holder.evento.setBackgroundResource(R.color.colorPhilanthropic);

        }

    }

    @Override
    public int getItemCount() {
        return listCalenderModel.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView extra;
        LinearLayout evento;
        public MyViewHolder(View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.textTitle);
            date = itemView.findViewById(R.id.tx_dt);
            extra = itemView.findViewById(R.id.tx_extra);
            evento = itemView.findViewById(R.id.event_ll);
        }
    }
}