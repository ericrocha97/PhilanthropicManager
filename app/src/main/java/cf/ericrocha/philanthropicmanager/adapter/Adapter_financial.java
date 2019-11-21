package cf.ericrocha.philanthropicmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cf.ericrocha.philanthropicmanager.R;
import cf.ericrocha.philanthropicmanager.model.FinancialModel;

/*public class Adapter_financial {
}*/
public class Adapter_financial extends RecyclerView.Adapter<Adapter_financial.MyViewHolder> {
    private List<FinancialModel> listFinancialModel;

    public Adapter_financial(List<FinancialModel> list) {
        this.listFinancialModel = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_financeiro, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FinancialModel financialModel = listFinancialModel.get( position );
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));
        String date = sdf.format(financialModel.getDate_lanc());
        holder.dt.setText( sdf.format(financialModel.getDate_lanc()));

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);


        holder.vl.setText("R$ " + df.format(financialModel.getValor()));
        if(financialModel.getTipo_lanc().equals("C")){
            holder.lancamento.setBackgroundResource(R.color.colorEventPass);

        }else {
            holder.lancamento.setBackgroundResource(R.color.colorPhilanthropic);
        }


        }



    @Override
    public int getItemCount() {
        return listFinancialModel.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vl;
        TextView dt;
        LinearLayout lancamento;
        public MyViewHolder(View itemView) {
            super(itemView);


            vl = itemView.findViewById(R.id.tx_vlrLancamento);
            dt = itemView.findViewById(R.id.tx_dtLancamento);
            lancamento = itemView.findViewById(R.id.financial_ll);
        }
    }
}