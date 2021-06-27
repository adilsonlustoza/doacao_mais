package br.com.lustoza.doacaomais;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco;
import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.ImageHelper;
import br.com.lustoza.doacaomais.Helper.PrefHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;
import br.com.lustoza.doacaomais.Services.Mapa.MapService;
import br.com.lustoza.doacaomais.Utils.MaskWatcher;

import static br.com.lustoza.doacaomais.Helper.ConstantHelper.CEP_MASK;

public class PerfilActivity extends _SuperActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    MapService mapService;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextLogradouro;
    EditText editTextBairro;
    EditText editTextCidade;
    Spinner spinnerTextEstado;
    EditText editTextCep;
    ImageView imageView;
    AlertDialog.Builder builder;
    AlertDialog alert;
    Endereco enderecoUser;
    Intent intent;
    FloatingActionButton floatingActionButton;
    Bitmap roundedCornerBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.CheckPermissions(new String[]{"android.permission.CAMERA"});
        }
        setContentView(R.layout.activity_perfil);
        this.ConfigureToolbar(ConstantHelper.ToolbarSubTitlePerfil);
        this.ConfigureReturnToolbar();
        this.Init();
    }

    private void Init() {
        editTextName = this.findViewById(R.id.txtNomePerfil);
        editTextEmail = this.findViewById(R.id.txtEmailPerfil);
        editTextLogradouro = this.findViewById(R.id.txtLogradourolPerfil);
        editTextBairro = this.findViewById(R.id.txtBairroPerfil);
        editTextCidade = this.findViewById(R.id.txtCidadePerfil);

        editTextCep = this.findViewById(R.id.txtCEPPerfil);
        editTextCep.addTextChangedListener(new MaskWatcher(CEP_MASK));

        spinnerTextEstado = this.findViewById(R.id.spinnerEstado);
        editTextCep = this.findViewById(R.id.txtCEPPerfil);
        imageView = this.findViewById(R.id.imgPerfil);

        floatingActionButton = this.findViewById(R.id.fab_enviar);
        floatingActionButton.setOnClickListener(this::GetAddress);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void onStart() {
        super.onStart();

        String nome = PrefHelper.getString(superContext, PrefHelper.PreferenciaNome);
        String email = PrefHelper.getString(superContext, PrefHelper.PreferenciaEmail);
        String estado = PrefHelper.getString(superContext, PrefHelper.PreferenciaEstado);
        String cidade = PrefHelper.getString(superContext, PrefHelper.PreferenciaCidade);
        String bairro = PrefHelper.getString(superContext, PrefHelper.PreferenciaBairro);
        String logradouro = PrefHelper.getString(superContext, PrefHelper.PreferenciaLogradouro);
        String cep = PrefHelper.getString(superContext, PrefHelper.PreferenciaCep);

        editTextName.setText(nome);
        editTextEmail.setText(email);
        editTextBairro.setText(bairro);
        editTextLogradouro.setText(logradouro);
        editTextCep.setText(cep);
        editTextCidade.setText(cidade);

        ArrayAdapter simpleAdapter = (ArrayAdapter) spinnerTextEstado.getAdapter();

        for (int i = 0; i < simpleAdapter.getCount(); i++) {
            String value = simpleAdapter.getItem(i).toString();
            if (estado != null && estado.contains(value))
                spinnerTextEstado.setSelection(i);
            else if (estado == null && value.contains("Paulo")) {
                spinnerTextEstado.setSelection(i);
            }

        }

        String image = PrefHelper.getString(this.getBaseContext(), PrefHelper.PreferenciaFoto);

        if (image != null && !TextUtils.isEmpty(image)) {
            Bitmap bitmap = ImageHelper.DecodeBase64(image);
            imageView.setImageBitmap(bitmap);
        }

    }

    public void GetAddress(final View view) {

        try {

            mapService = new MapService(getBaseContext());

            int id = view.getId();

            if (id == R.id.fab_enviar) {

                this.runOnUiThread(() -> {

                    if (
                            TextUtils.isEmpty(editTextLogradouro.getText().toString()) ||
                                    TextUtils.isEmpty(editTextBairro.getText().toString()) ||
                                    TextUtils.isEmpty(editTextCidade.getText().toString())
                    ) {

                        Snackbar snackbar = Snackbar
                                .make(view, R.string.PreenchimentoEndereco, Snackbar.LENGTH_LONG);

                        snackbar.show();

                        return;
                    }

                    if (Geocoder.isPresent()) {
                        enderecoUser = new Endereco
                                (
                                        editTextLogradouro.getText().toString(),
                                        editTextBairro.getText().toString(),
                                        editTextCidade.getText().toString(),
                                        spinnerTextEstado.getSelectedItem().toString(),
                                        editTextCep.getText().toString()
                                );

                        final List<Endereco> resultEnderecos = mapService.ConfirmarEndereco(enderecoUser);
                        enderecoUser = null;

                        if (resultEnderecos == null) {
                            Snackbar
                                    .make(view,
                                            R.string.NaoFoiPossivelValidarLocalizacao,
                                            Snackbar.LENGTH_LONG)
                                    .show();
                        } else if (resultEnderecos.size() <= 1) {
                            enderecoUser = resultEnderecos.get(0);
                            globalView = view;
                            SaveAddress(enderecoUser);
                        } else {
                            ArrayList<String> correctAddress = new ArrayList<>();
                            int i = resultEnderecos.size() - 1;

                            while (i >= 0) {
                                enderecoUser = resultEnderecos.get(i);

                                if (
                                        (enderecoUser.getLogradouro() != null && !TextUtils.isEmpty(enderecoUser.getLogradouro()))
                                                &&
                                                (enderecoUser.getBairro() != null && !TextUtils.isEmpty(enderecoUser.getBairro()))
                                                &&
                                                (enderecoUser.getCidade() != null && !TextUtils.isEmpty(enderecoUser.getCidade()))
                                )

                                    correctAddress.add(
                                            enderecoUser.getLogradouro() + "-" +
                                                    enderecoUser.getBairro() + "-" +
                                                    enderecoUser.getCidade()
                                    );
                                else
                                    resultEnderecos.remove(i);
                                i--;
                            }

                            builder = null;
                            builder = new AlertDialog.Builder(superContext);

                            if (correctAddress.size() > 0) {
                                //Notifica a Activity para tomar alguma acao
                                builder.setTitle("Selecione seu endereço");
                                builder.setIcon(R.drawable.information_24);

                                final String[] listPass = correctAddress.toArray(new String[0]);

                                builder.setSingleChoiceItems(listPass, -1, (dialog, which) -> {
                                    String[] separate = listPass[which].split("-");
                                    Endereco correctEndereco = null;

                                    for (Endereco filterEndereco : resultEnderecos) {
                                        if (filterEndereco.getLogradouro().toLowerCase().trim().equals(separate[0].toLowerCase().trim())) {
                                            correctEndereco = filterEndereco;
                                        }

                                    }

                                    globalView = view;
                                    dialog.dismiss();
                                    SaveAddress(correctEndereco);
                                });
                                alert = builder.create();
                                alert.show();
                            }
                        }

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(view, "Seu dispositivo nao suporta GeoCoordenadas", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                });

            }
        } catch (Exception e) {
            TrackHelper.WriteError(this, "GetAddress", e.getMessage());
        }

    }

    @SuppressWarnings("rawtypes")
    private void SaveAddress(Endereco enderecoUser) {
        try {

            if (!TextUtils.isEmpty(enderecoUser.getBairro()))
                editTextBairro.setText(enderecoUser.getBairro());

            if (!TextUtils.isEmpty(enderecoUser.getCep()))
                editTextCep.setText(enderecoUser.getCep());

            if (!TextUtils.isEmpty(enderecoUser.getLogradouro()))
                editTextLogradouro.setText(enderecoUser.getLogradouro());

            if (!TextUtils.isEmpty(enderecoUser.getCidade()))
                editTextCidade.setText(enderecoUser.getCidade());

            String estado = spinnerTextEstado.getSelectedItem().toString();
            ArrayAdapter simpleAdapter = (ArrayAdapter) spinnerTextEstado.getAdapter();

            for (int i = 0; i < simpleAdapter.getCount(); i++) {
                String value = simpleAdapter.getItem(i).toString();
                if (estado != null && estado.contains(value))
                    spinnerTextEstado.setSelection(i);
            }

            if (enderecoUser.getLatitude() == 0 && enderecoUser.getLongitude() == 0) {
                Snackbar
                        .make(globalView, "Complete seu endereço, não foi possível determinar a sua localização para o mapa.", Snackbar.LENGTH_LONG)
                        .show();

                return;
            }

            PrefHelper.setString(superContext, PrefHelper.PreferenciaEstado, enderecoUser.getEstado());
            PrefHelper.setString(superContext, PrefHelper.PreferenciaCidade, enderecoUser.getCidade() == null ? editTextCidade.getText().toString() : enderecoUser.getCidade());
            PrefHelper.setString(superContext, PrefHelper.PreferenciaBairro, enderecoUser.getBairro() == null ? editTextBairro.getText().toString() : enderecoUser.getBairro());
            PrefHelper.setString(superContext, PrefHelper.PreferenciaLogradouro, enderecoUser.getLogradouro() == null ? editTextLogradouro.getText().toString() : enderecoUser.getLogradouro());
            PrefHelper.setString(superContext, PrefHelper.PreferenciaCep, enderecoUser.getCep());
            PrefHelper.setString(superContext, PrefHelper.PreferenciaPais, enderecoUser.getPais());
            PrefHelper.setString(superContext, PrefHelper.PreferenciaLatitude, String.valueOf(enderecoUser.getLatitude()));
            PrefHelper.setString(superContext, PrefHelper.PreferenciaLongitude, String.valueOf(enderecoUser.getLongitude()));

            Snackbar
                    .make(globalView, "Endereço salvo. Ver no mapa?", Snackbar.LENGTH_LONG)
                    .setAction("Sim", v -> {
                        intent = new Intent(superContext, MapsActivity.class);
                        bundle = new Bundle();
                        bundle.putString(ConstantHelper.objActivity, "Perfil");
                        intent.putExtra(ConstantHelper.objBundle, bundle);
                        startActivity(intent);
                    })
                    .show();
        } catch (Exception e) {
            TrackHelper.WriteError(this, "StoreAddress", e.getMessage());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                roundedCornerBitmap = ImageHelper.getCircularBitmap(bitmap);
                PrefHelper.setString(superContext, PrefHelper.PreferenciaFoto, ImageHelper.EncodeTobase64(roundedCornerBitmap));
                ConstantHelper.foiTrocadaImagemPerfil = true;
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "onActivityResult", e.getMessage());
        }

    }

    public void GetPhoto(View view) {
        try {

            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        } catch (Exception ex) {
            TrackHelper.WriteError(this, "GetPhoto", ex.getMessage());
        }

    }

}
