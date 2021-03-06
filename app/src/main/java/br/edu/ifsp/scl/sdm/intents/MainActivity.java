package br.edu.ifsp.scl.sdm.intents;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import br.edu.ifsp.scl.sdm.intents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding activityMainBinding;

    private ActivityResultLauncher<String> requisicaoPermissaoActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

       /* Toolbar toolbar = activityMainBinding.mainTb.appTb;
        toolbar.setTitle("Tratando Intents");
        toolbar.setSubtitle("Principais tipos");

        setSupportActionBar(toolbar);*/

        requisicaoPermissaoActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if(result) {
                            requisitarPermissaoLigacao();
                        } else {
                            discarTelefone();
                        }
                    }
                });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean retorno = false;
        switch(item.getItemId()) {
            case R.id.viewMi:
                String url = activityMainBinding.parameterEt.getText().toString();
                if (!url.contains("http")) {
                    String newUrl = "https://" + url;
                    url = newUrl;
                }
                Intent siteIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                startActivity(siteIntent);
                retorno = true;
                break;
            case R.id.exitMi:
                this.finish();
                retorno = true;
                break;
            case R.id.chooserMi:
                retorno = true;
                break;
            case R.id.pickMi:
                retorno = true;
                break;
            case R.id.dialMi:
                Intent ligacaoIntent = new Intent(Intent.ACTION_DIAL);
                ligacaoIntent.setData(Uri.parse("tel: " + activityMainBinding.parameterEt.getText()));
                startActivity(ligacaoIntent);
                retorno = true;
                break;
            case R.id.callMi:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        //REQUISITAR PERMISS????O
                        requisitarPermissaoLigacao();
                    } else {
                        // Executar discagem
                        discarTelefone();
                    }
                } else {
                    // Executar discagem
                    discarTelefone();
                }
                retorno = true;
                break;
            case R.id.actionMi:
                Intent actionIntent = new Intent("OPEN_ACTION_ACTIVITY").putExtra(
                        Intent.EXTRA_TEXT,
                        activityMainBinding.parameterEt.getText().toString()
                );
                startActivity(actionIntent);
                retorno = true;
                break;
            default:
                retorno = false;
        }

        return retorno;
    }

    private final void discarTelefone() {
        Intent discarIntent = new Intent();
        discarIntent.setAction(Intent.ACTION_CALL);
        discarIntent.setData(Uri.parse((new StringBuilder()).append("tel: " + activityMainBinding.parameterEt.getText()).toString()));
        startActivity(discarIntent);
    }

    private final void requisitarPermissaoLigacao() {
        requisicaoPermissaoActivityResultLauncher.launch(Manifest.permission.CALL_PHONE);
    }
}