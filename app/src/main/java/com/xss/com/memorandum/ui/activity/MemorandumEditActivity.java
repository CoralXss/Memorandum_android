package com.xss.com.memorandum.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xss.com.memorandum.R;
import com.xss.com.memorandum.cache.preference.SharedPrefUtil;
import com.xss.com.memorandum.model.MemorandumModel;
import com.xss.com.memorandum.ui.activity.base.BaseActivity;
import com.xss.com.memorandum.ui.adapter.BaseRecyclerAdapter;
import com.xss.com.memorandum.ui.adapter.FontColorAdapter;
import com.xss.com.memorandum.utils.Constants;
import com.xss.com.memorandum.utils.DateUtils;
import com.xss.com.memorandum.widget.customdialog.DelDataDialog;
import com.xss.com.memorandum.widget.customdialog.FontSetPopWindow;
import com.xss.com.memorandum.widget.customdialog.InsertPictureDialog;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/16 16:00
 */
public class MemorandumEditActivity extends BaseActivity implements View.OnClickListener {
    private static final String CREATE_LABEL = "创建时间 ";
    private static final String UPDATE_LABEL = "修改时间 ";
    private static final String FROM_WHERE = "from_where";
    private static final String MEMORANDUM_ITEM = "memorandum_item";
    private static final String MEMO_TITLE = "memo_title";
    private static final String CATEGORY_ID = "category_id";

    private static final int FROM_ADD = 1;
    private static final int FROM_LIST = 2;
    private int from_where = -1;

    private String memo_title;
    private Integer category_id;
    private MemorandumModel memorandumModel;

    private static final float MIN_TXT_SZIE = 12;
    private static final float MAX_TXT_SIZE = 24;

    private EditText edt_title;
    private EditText edt_content;
    private ImageView img_picture;
    private TextView tv_create_time;
    private ImageView img_save;

    private ImageView img_insert_pic, img_font, img_color, img_share, img_delete;

    private InsertPictureDialog insertPictureDialog;
    private TextView tv_from_pictures, tv_from_files, tv_from_camera;
    private static final int RESULT_LOAD_IMAGE_FROM_GALLERY = 1;  //从图库选取
    private static final int RESULT_LOAD_IMAGE_FROM_CAMERA = 2;  //从相机获取

    private FontSetPopWindow fontSetPopWindow;
    private SeekBar seekBar;
    private ImageView img_font_s, img_font_l;
    private RecyclerView rv_font_colors;

    private SharedPrefUtil sharedPrefUtil;

    private DelDataDialog delDataDialog = null;

    public static void startEditActivity(Activity startActivity, int from_where, MemorandumModel model, String memo_title, Integer category_id) {
        Intent intent = new Intent(startActivity, MemorandumEditActivity.class);
        intent.putExtra(FROM_WHERE, from_where);
        if (model != null) {
            intent.putExtra(MEMORANDUM_ITEM, model);
        }
        if (memo_title != null) {
            intent.putExtra(MEMO_TITLE, memo_title);
            intent.putExtra(CATEGORY_ID, category_id);
        }
        startActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorandum_edit);

        if (getIntent() != null) {
            from_where = getIntent().getIntExtra(FROM_WHERE, -1);
            memorandumModel = (MemorandumModel) getIntent().getSerializableExtra(MEMORANDUM_ITEM);
            memo_title = getIntent().getStringExtra(MEMO_TITLE);
            category_id = getIntent().getIntExtra(CATEGORY_ID, 0);
        }

        sharedPrefUtil = SharedPrefUtil.getInstance(this);

        initView();
        initData();
    }

    @Override
    protected void initView() {
        toolbar = $(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        // 给左上角图标的左边加上一个返回的图标, 对应id为android.R.id.home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img_save = $(R.id.img_save);
        edt_title = $(R.id.edt_title);
        edt_content = $(R.id.edt_content);
        img_picture = $(R.id.img_picture);
        tv_create_time = $(R.id.tv_create_time);

        img_insert_pic = $(R.id.img_insert_pic);
        img_font = $(R.id.img_font);
        img_color = $(R.id.img_color);
        img_share = $(R.id.img_share);
        img_delete = $(R.id.img_delete);

        img_save.setOnClickListener(this);
        img_insert_pic.setOnClickListener(this);
        img_font.setOnClickListener(this);
        img_color.setOnClickListener(this);
        img_share.setOnClickListener(this);
        img_delete.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String current_time = DateUtils.getHourMinute(DateUtils.getCurrentTime());
        Log.e("time", current_time);
        if (from_where != -1) {
            if (from_where == Constants.FROM_LIST_CREATE) {
                tv_create_time.setText(CREATE_LABEL);
                edt_title.setText(memo_title);
                edt_title.setEnabled(false);

            } else if (from_where == Constants.FROM_LIST_ITEM) {
                tv_create_time.setText(UPDATE_LABEL);
                if (memorandumModel != null) {
                    edt_title.setText(memorandumModel.getTitle());
                    edt_content.setText(memorandumModel.getContent());
                    Log.e("time", (memorandumModel.getCreate_date()==null)?"null":memorandumModel.getCreate_date());
                }
            }
            tv_create_time.append(current_time);
        }

        // 设置存储的 内容的字体大小和颜色
        float progress = sharedPrefUtil.getSeekBarProgress();
        float contentTxtSize = MIN_TXT_SZIE + (MAX_TXT_SIZE - MIN_TXT_SZIE) * (progress / 100);

        edt_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentTxtSize);

//        int color = sharedPrefUtil.getContentTxtColor();
//        edt_content.setTextColor(color);
    }

    private String imagePath;
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_save) {
            if (TextUtils.isEmpty(edt_content.getText())) {

            } else {
                if (from_where == Constants.FROM_LIST_ITEM) {
                    //  修改
                    updateMemorandum();
                } else if (from_where == Constants.FROM_LIST_CREATE) {
                    addMemorandum();
                }
            }
            finish();
        } else if (view.getId() == R.id.img_insert_pic) {  //插入图片
            openInsertDialog();
        } else if (view.getId() == R.id.img_font) {        //设置字体
            showFontSetPopWindow();
        } else if (view.getId() == R.id.img_color) {       //设置背景色

        } else if (view.getId() == R.id.img_share) {       //分享

        } else if (view.getId() == R.id.img_delete) {      //删除
            showDeleteDialog();
        } else if (view.getId() == R.id.tv_from_pictures) { //从图库选择照片
            insertPictureDialog.closeDialog();
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE_FROM_GALLERY);

        } else if (view.getId() == R.id.tv_from_files) {    //从文档选择照片
            getPictureFromFiles();
        } else if (view.getId() == R.id.tv_from_camera) {   //从照相机选择图片
            insertPictureDialog.closeDialog();

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, RESULT_LOAD_IMAGE_FROM_CAMERA);

        } else if (view.getId() == R.id.img_font_s) {  //放大字体

        } else if (view.getId() == R.id.img_font_l) {  //缩小字体

        }
    }

    /**
     * 添加 数据到 数据库
     */
    private void addMemorandum() {
        MemorandumModel model = new MemorandumModel();
        if (TextUtils.isEmpty(edt_title.getText())) {
            model.setTitle("");
        } else {
            model.setTitle(edt_title.getText().toString());
        }
        model.setContent(edt_content.getText().toString());
        model.setCreate_date(String.valueOf(DateUtils.getCurrentTime()));
        model.setCategory_id(category_id); //默认类型为全部

        // 标题和内容一样 就不添加
        if ( !memorandumTableService.checkMemorandum(model) ) {
            memorandumTableService.addMemorandum(model);
        }
    }

    private void updateMemorandum() {
        MemorandumModel model = new MemorandumModel();
        model.set_id(memorandumModel.get_id());
        if (TextUtils.isEmpty(edt_title.getText())) {
            model.setTitle("");
        } else {
            model.setTitle(edt_title.getText().toString());
        }
        model.setContent(edt_content.getText().toString());
        model.setCreate_date(String.valueOf(DateUtils.getCurrentTime()));
        model.setCategory_id(category_id); //默认类型为全部

        memorandumTableService.updateMemorandum(model);

    }

    private void delMemorandum() {
        if (memorandumTableService.delMemorandum(memorandumModel)) {
            Toast.makeText(MemorandumEditActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MemorandumEditActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteDialog() {
        if (from_where == Constants.FROM_LIST_CREATE) {
            Toast.makeText(MemorandumEditActivity.this, "没有数据可删除", Toast.LENGTH_SHORT).show();
        } else if (from_where == Constants.FROM_LIST_ITEM) {
            if (delDataDialog == null) {
                delDataDialog = new DelDataDialog(MemorandumEditActivity.this, true);
            }
            delDataDialog.showDialog();
            Button btn_ok = delDataDialog.getBtn_ok();
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delMemorandum();
                    delDataDialog.closeDialog();
                    MemorandumEditActivity.this.finish();
                }
            });
        }
    }

    /**
     * 打开 插入图片对话框
     */
    private void openInsertDialog() {
        insertPictureDialog = new InsertPictureDialog(MemorandumEditActivity.this, true);
        tv_from_pictures = insertPictureDialog.getTv_from_pictures();
        tv_from_files = insertPictureDialog.getTv_from_files();
        tv_from_camera = insertPictureDialog.getTv_from_camera();

        tv_from_pictures.setOnClickListener(this);
        tv_from_files.setOnClickListener(this);
        tv_from_camera.setOnClickListener(this);

        insertPictureDialog.showDialog();
    }

    /**
     * 打开设置字体 弹出框
     */
    private void showFontSetPopWindow() {
        fontSetPopWindow = new FontSetPopWindow(MemorandumEditActivity.this);
        fontSetPopWindow.showAtViewTop(findViewById(R.id.ll_bottom_line));

        img_font_s = fontSetPopWindow.getImg_font_s();
        img_font_l = fontSetPopWindow.getImg_font_l();
        seekBar = fontSetPopWindow.getSeekBar();
        rv_font_colors = fontSetPopWindow.getRv_font_colors();

        img_font_s.setOnClickListener(this);
        img_font_l.setOnClickListener(this);

        // 设置seekBar的进度
        float progress = sharedPrefUtil.getSeekBarProgress();
        seekBar.setProgress((int) progress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float txtSize = MIN_TXT_SZIE + (MAX_TXT_SIZE - MIN_TXT_SZIE) * ((float) seekBar.getProgress() / 100);
                edt_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sharedPrefUtil.setSeekBarProgress(seekBar.getProgress());
            }
        });

        final FontColorAdapter adapter = (FontColorAdapter) rv_font_colors.getAdapter();
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                sharedPrefUtil.setContentTxtColor((Integer) object);
                edt_content.setTextColor((Integer) object);

                boolean[] checked = adapter.getChecked();
                checked[position] = true;
                for (int i = 0; i < checked.length; i++) {
                    if (i != position) {
                        checked[i] = false;
                    }
                }
                adapter.setChecked(checked);
                adapter.notifyDataSetChanged();
            }
        });

    }

    /**
     * 从图库中选取图片，并更新UI
     * @param data
     */
    private void setPictureFromGallery(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        edt_content.setVisibility(View.GONE);
        img_picture.setVisibility(View.VISIBLE);
        img_picture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    }

    private void getPictureFromFiles() {

    }

    /**
     * 相机获取图片
     * @param data
     */
    private void setPictureFromCamera(Intent data) {
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//        if (bitmap != null) {
//            edt_content.setVisibility(View.GONE);
//            img_picture.setVisibility(View.VISIBLE);
//            img_picture.setImageBitmap(bitmap);
//        }

        final Bitmap bitmap = data.getParcelableExtra("data");
        if (bitmap != null) {
            if (bitmap != null) {
                edt_content.setVisibility(View.GONE);
                img_picture.setVisibility(View.VISIBLE);
                img_picture.setImageBitmap(bitmap);
            }
        }
    }

    private void doCropPhoto(Bitmap bitmap) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.putExtra("data", bitmap);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_LOAD_IMAGE_FROM_GALLERY:
                    setPictureFromGallery(data);
                    break;
                case RESULT_LOAD_IMAGE_FROM_CAMERA:
                    setPictureFromCamera(data);
                    break;
                case 5:
                    Bitmap bitmap = data.getParcelableExtra("data");
//                    Log.e("data", (bitmap == null) +"");
                    if (bitmap != null) {
                        edt_content.setVisibility(View.GONE);
                        img_picture.setVisibility(View.VISIBLE);
                        img_picture.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }
}
