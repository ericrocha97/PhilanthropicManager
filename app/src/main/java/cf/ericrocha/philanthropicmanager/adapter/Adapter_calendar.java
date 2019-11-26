package cf.ericrocha.philanthropicmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.R;
import cf.ericrocha.philanthropicmanager.model.CalenderModel;

public class Adapter_calendar extends RecyclerView.Adapter<Adapter_calendar.MyViewHolder> {
    private List<CalenderModel> listCalenderModel;

    public Adapter_calendar(List<CalenderModel> list) {
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
        String dt = sdf.format(calenderModel.getDate());
        holder.date.setText( sdf.format(calenderModel.getDate()));

        holder.situacao.setText("Pendente");
        if(calenderModel.getCor().equals(0)){
            holder.evento.setBackgroundResource(R.color.colorWork);
            holder.tipo.setText("Trabalho");
            holder.extra.setText( "Membro: " + calenderModel.getExtra() );

        }else{
            holder.evento.setBackgroundResource(R.color.colorPhilanthropic);
            holder.tipo.setText("Filantropia");
            holder.extra.setText( "Local: " + calenderModel.getExtra() );

        }
        Date today = new Date();
        if(calenderModel.getDate().before(today)){
            //holder.evento.setVisibility(View.INVISIBLE);
            holder.situacao.setText("Concluido");
            holder.evento.setBackgroundResource(R.color.colorEventPass);
            //listCalenderModel.remove(position);

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
        TextView tipo;
        TextView situacao;
        LinearLayout evento;
        public MyViewHolder(View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.tx_titulo);
            date = itemView.findViewById(R.id.tx_date);
            extra = itemView.findViewById(R.id.tx_extra);
            tipo = itemView.findViewById(R.id.tipo);
            situacao = itemView.findViewById(R.id.Situacao);
            evento = itemView.findViewById(R.id.event_ll);
        }
    }
}