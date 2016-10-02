package noob.com.noobfilechooser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.noob.noobfilechooser.NoobFileActivity;
import com.noob.noobfilechooser.listeners.OnNoobFileSelected;
import com.noob.noobfilechooser.managers.NoobManager;
import com.noob.noobfilechooser.models.NoobFile;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    NoobFile mNoobFile;
    TextView mNameText, mTypeText, mUriText;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNameText = (TextView) findViewById(R.id.file_name_text);
        mTypeText = (TextView) findViewById(R.id.file_type_text);
        mUriText = (TextView) findViewById(R.id.file_uri_text);
        mImageView = (ImageView) findViewById(R.id.image_view);
    }

    public void setNoobFile(NoobFile noobFileParam) {
        mNoobFile = noobFileParam;
        mNameText.setText(mNoobFile.getName());
        mTypeText.setText(mNoobFile.getType());
        mUriText.setText(mNoobFile.getUri().toString());
        if(mNoobFile.isImageFile()){
            mImageView.setVisibility(View.VISIBLE);
            mImageView.setImageURI(mNoobFile.getUri());
        }else{
            mImageView.setVisibility(View.INVISIBLE);
        }
    }

    public void onChooseFileClick(View view) {
        NoobManager.getInstance().setNoobFileSelectedListener(new OnNoobFileSelected() {
            @Override
            public void onSingleFileSelection(NoobFile file) {
                setNoobFile(file);
            }

            @Override
            public void onMultipleFilesSelection(List<NoobFile> files) {

            }
        });
        Intent _intent = new Intent(this, NoobFileActivity.class);
        startActivity(_intent);
    }

    public void onDeleteFileClick(View view) {
        if (mNoobFile == null)
            return;
        mNoobFile.delete();
        if (!mNoobFile.getDocumentFile().exists()) {
            Toast.makeText(this, mNoobFile.getName() + " is deleted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Deletion failed", Toast.LENGTH_LONG).show();
        }
    }

    public void onRenameFileClick(View view) {
        final EditText inputEdit = new EditText(this);
        inputEdit.setHint("Please enter the new file name with extension");

        new AlertDialog.Builder(this)
                .setTitle("Rename File")
                .setMessage("Please choose a file name with extension")
                .setView(inputEdit)
                .setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterfaceParam, int iParam) {
                        if (inputEdit.getText() != null && !inputEdit.getText().toString().isEmpty()) {
                            mNoobFile.renameTo(inputEdit.getText().toString());
                            Toast.makeText(MainActivity.this, "Rename successful", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .create()
                .show();
    }
}
