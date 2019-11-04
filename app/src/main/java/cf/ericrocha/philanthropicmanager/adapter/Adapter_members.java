package cf.ericrocha.philanthropicmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cf.ericrocha.philanthropicmanager.R;
import cf.ericrocha.philanthropicmanager.model.MembersModel;

public class Adapter_members extends RecyclerView.Adapter<Adapter_members.MyViewHolder> {
    private List<MembersModel> listMembersModel;

    public Adapter_members(List<MembersModel> list) {
        this.listMembersModel = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_membres, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MembersModel membersModel = listMembersModel.get( position );
        holder.nome.setText( "Nome: " + membersModel.getNome() );
        holder.nivel.setText( "Grau: " + membersModel.getNivel().toString() );
        holder.telefone.setText("Telefone: " + membersModel.getTelefone() );
        holder.cid.setText( "CID: " +membersModel.getCid() );



    }

    @Override
    public int getItemCount() {
        return listMembersModel.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        TextView nivel;
        TextView telefone;
        TextView cid;
        LinearLayout membro;
        public MyViewHolder(View itemView) {
            super(itemView);


            nome = itemView.findViewById(R.id.tx_nome);
            nivel = itemView.findViewById(R.id.tx_nivel);
            telefone = itemView.findViewById(R.id.Telefone);
            cid = itemView.findViewById(R.id.tx_cid);
            membro = itemView.findViewById(R.id.member_ll);
        }
    }
}