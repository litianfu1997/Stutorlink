package com.nnxy.putout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.nnxy.entity.QuesCard;
import com.nnxy.stutorlink.R;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2017/12/21 下午4:35
 */
public class MainFragment extends Fragment implements MediaAdapter.OnAddMediaListener
        , View.OnClickListener {

    private int REQUEST_CODE = 0x000111;
    private MediaAdapter mMediaAdapter;
    private RecyclerView recyclerView;
    private List<MediaEntity> result;
    private EditText editTextContent;
    private Button putOutBtn;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity().getWindow() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_phoenix_demo, container, false);
//        view.findViewById(R.id.btn_compress_picture).setOnClickListener(this);
//        view.findViewById(R.id.btn_compress_video).setOnClickListener(this);
//        view.findViewById(R.id.btn_take_picture).setOnClickListener(this);3
        recyclerView =  view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false));

        mMediaAdapter = new MediaAdapter(this);
        recyclerView.setAdapter(mMediaAdapter);
        mMediaAdapter.setOnItemClickListener(new MediaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mMediaAdapter.getData().size() > 0) {
                    //预览
                    Phoenix.with()
                            .pickedMediaList(mMediaAdapter.getData())
                            .start(getActivity(), PhoenixOption.TYPE_BROWSER_PICTURE, 0);
                }
            }
        });
        return view;
    }

    @Override
    public void onaddMedia() {
        Phoenix.with()
                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                .fileType(MimeType.ofAll())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(10)// 最大选择数量
                .minPickNumber(0)// 最小选择数量
                .spanCount(4)// 每行显示个数
                .enablePreview(true)// 是否开启预览
                .enableCamera(true)// 是否开启拍照
                .enableAnimation(true)// 选择界面图片点击效果
                .enableCompress(true)// 是否开启压缩
                .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
                .thumbnailHeight(160)// 选择界面图片高度
                .thumbnailWidth(160)// 选择界面图片宽度
                .enableClickSound(false)// 是否开启点击声音
                .pickedMediaList(mMediaAdapter.getData())// 已选图片数据
                .videoFilterTime(30)//显示多少秒以内的视频
                .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                .start(this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //返回的数据
            result = Phoenix.result(data);
            mMediaAdapter.setData(result);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextContent = view.findViewById(R.id.et_content);
        putOutBtn = view.findViewById(R.id.btn_putout);
        putOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                QuesCard quesCard =new QuesCard();
                quesCard.setQuesCardId(1);
                quesCard.setUserId(1);
                quesCard.setCommentNum(100);
                quesCard.setLikeNum(66);
                quesCard.setTitle("这是个测试");
                quesCard.setPutoutTime("2019-5-30");
                if (result != null){
                    for(MediaEntity tt : result){
//                System.out.println(tt.getLocalPath());
//                System.out.println(getImageStr(tt.getLocalPath()));
//                System.out.println(gson.toJson( getImageStr(tt.getLocalPath())));
                        quesCard.setImgBase64(Collections.singletonList(gson.toJson(getImageStr(tt.getLocalPath()))));
                    }
                    System.out.println(quesCard.toString());
                }else {
                    System.out.println(quesCard.toString());
                    System.out.println("没有选择图片");
                }



            }
        });


    }
    /**
     * 将图片文件转为字符串
     * @param imgFile
     * @return
     */
    public static String getImageStr(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //String imgFile = "d:\\111.jpg";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64.Encoder encoder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            encoder = Base64.getEncoder();
        }
        // 返回Base64编码过的字节数组字符串
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            return encoder.encodeToString(data);
        }
        return null;
    }

    /**
     * 将字符串转为图片
     * @param imgStr
     * @return
     */
    public static boolean generateImage(String imgStr,String imgFile)throws Exception {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        // 对字节数组Base64编码
        Base64.Decoder decoder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            decoder = Base64.getDecoder();
        }
        try {
            // Base64解码
            byte[] b = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                b = decoder.decode(imgStr);
            }
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = imgFile;// 新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void onClick(View v) {
    }
}
