package com.silence.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.silence.caipu.R;
import com.silence.utils.Const;
import com.silence.utils.FileUtils;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean exist = sharedPreferences.getBoolean(Const.SP_KEY, false);
        if (!exist) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Const.SP_KEY, true);
            editor.apply();
            new FileTask().execute();
        } else {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    startMain();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, Const.DELAY_TIME);
        }

    }

    private void startMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void init() {
        int[] imageId = {R.raw.recipe_1, R.raw.recipe_2, R.raw.recipe_3, R.raw.recipe_4, R.raw.recipe_5
                , R.raw.recipe_6, R.raw.recipe_7, R.raw.recipe_8, R.raw.recipe_9, R.raw.recipe_10
                , R.raw.recipe_11, R.raw.recipe_12, R.raw.recipe_13, R.raw.recipe_14, R.raw.recipe_15
                , R.raw.recipe_16, R.raw.recipe_17, R.raw.recipe_18, R.raw.recipe_19, R.raw.recipe_20
                , R.raw.recipe_21, R.raw.recipe_22, R.raw.recipe_23, R.raw.recipe_24, R.raw.recipe_25
                , R.raw.recipe_26, R.raw.recipe_27, R.raw.recipe_28, R.raw.recipe_29, R.raw.recipe_30
                , R.raw.recipe_31, R.raw.recipe_32, R.raw.recipe_33, R.raw.recipe_34, R.raw.recipe_35
                , R.raw.recipe_36, R.raw.recipe_37, R.raw.recipe_38, R.raw.recipe_39, R.raw.recipe_40
                , R.raw.recipe_41, R.raw.recipe_42, R.raw.recipe_43, R.raw.recipe_44, R.raw.recipe_45
                , R.raw.recipe_46, R.raw.recipe_47, R.raw.recipe_48, R.raw.recipe_49, R.raw.recipe_50
                , R.raw.recipe_51, R.raw.recipe_52, R.raw.recipe_53, R.raw.recipe_54, R.raw.recipe_55
                , R.raw.recipe_56, R.raw.recipe_57, R.raw.recipe_58, R.raw.recipe_59, R.raw.recipe_60
                , R.raw.recipe_61, R.raw.recipe_62, R.raw.recipe_63, R.raw.recipe_64, R.raw.recipe_65
                , R.raw.recipe_66, R.raw.recipe_67, R.raw.recipe_68, R.raw.recipe_69, R.raw.recipe_70
                , R.raw.recipe_71, R.raw.recipe_72, R.raw.recipe_73, R.raw.recipe_74, R.raw.recipe_75
                , R.raw.recipe_76, R.raw.recipe_77, R.raw.recipe_78, R.raw.recipe_79, R.raw.recipe_80
                , R.raw.recipe_81, R.raw.recipe_82, R.raw.recipe_83, R.raw.recipe_84, R.raw.recipe_85
                , R.raw.recipe_86, R.raw.recipe_87, R.raw.recipe_88, R.raw.recipe_89, R.raw.recipe_90
                , R.raw.recipe_91, R.raw.recipe_92, R.raw.recipe_93, R.raw.recipe_94, R.raw.recipe_95
                , R.raw.recipe_96, R.raw.recipe_97, R.raw.recipe_98, R.raw.recipe_99, R.raw.recipe_100
                , R.raw.recipe_101, R.raw.recipe_102, R.raw.recipe_103, R.raw.recipe_104, R.raw.recipe_105
                , R.raw.recipe_106, R.raw.recipe_107, R.raw.recipe_108, R.raw.recipe_109, R.raw.recipe_110
                , R.raw.recipe_111, R.raw.recipe_112, R.raw.recipe_113, R.raw.recipe_114, R.raw.recipe_115
                , R.raw.recipe_116, R.raw.recipe_117, R.raw.recipe_118, R.raw.recipe_119, R.raw.recipe_120
                , R.raw.recipe_121, R.raw.recipe_122, R.raw.recipe_123, R.raw.recipe_124, R.raw.recipe_125
                , R.raw.recipe_126, R.raw.recipe_127, R.raw.recipe_128, R.raw.recipe_129, R.raw.recipe_130
                , R.raw.recipe_131, R.raw.recipe_132, R.raw.recipe_133, R.raw.recipe_134, R.raw.recipe_135
                , R.raw.recipe_136, R.raw.recipe_137, R.raw.recipe_138, R.raw.recipe_139, R.raw.recipe_140
                , R.raw.recipe_141, R.raw.recipe_142, R.raw.recipe_143, R.raw.recipe_144, R.raw.recipe_145
                , R.raw.recipe_146, R.raw.recipe_147, R.raw.recipe_148, R.raw.recipe_149, R.raw.recipe_150
                , R.raw.recipe_151, R.raw.recipe_152, R.raw.recipe_153, R.raw.recipe_154, R.raw.recipe_155
                , R.raw.recipe_156, R.raw.recipe_157, R.raw.recipe_158, R.raw.recipe_159, R.raw.recipe_160
                , R.raw.recipe_161, R.raw.recipe_162, R.raw.recipe_163, R.raw.recipe_164, R.raw.recipe_165
                , R.raw.recipe_166, R.raw.recipe_167, R.raw.recipe_168, R.raw.recipe_169, R.raw.recipe_170
                , R.raw.recipe_171, R.raw.recipe_172, R.raw.recipe_173, R.raw.recipe_174, R.raw.recipe_175
                , R.raw.recipe_176, R.raw.recipe_177, R.raw.recipe_178, R.raw.recipe_179, R.raw.recipe_180
                , R.raw.recipe_181, R.raw.recipe_182, R.raw.recipe_183, R.raw.recipe_184, R.raw.recipe_185
                , R.raw.recipe_186, R.raw.recipe_187, R.raw.recipe_188, R.raw.recipe_189, R.raw.recipe_190
                , R.raw.recipe_191, R.raw.recipe_192, R.raw.recipe_193, R.raw.recipe_194, R.raw.recipe_195
                , R.raw.recipe_196, R.raw.recipe_197, R.raw.recipe_198, R.raw.recipe_199, R.raw.recipe_200
                , R.raw.recipe_201, R.raw.recipe_202, R.raw.recipe_203, R.raw.recipe_204, R.raw.recipe_205
                , R.raw.recipe_206, R.raw.recipe_207, R.raw.recipe_208, R.raw.recipe_209, R.raw.recipe_210
                , R.raw.recipe_211, R.raw.recipe_212, R.raw.recipe_213, R.raw.recipe_214, R.raw.recipe_215
                , R.raw.recipe_216, R.raw.recipe_217, R.raw.recipe_218, R.raw.recipe_219, R.raw.recipe_220
                , R.raw.recipe_221, R.raw.recipe_222, R.raw.recipe_223, R.raw.recipe_224, R.raw.recipe_225
                , R.raw.recipe_226, R.raw.recipe_227, R.raw.recipe_228, R.raw.recipe_229, R.raw.recipe_230
                , R.raw.recipe_231, R.raw.recipe_232, R.raw.recipe_233, R.raw.recipe_234, R.raw.recipe_235
                , R.raw.recipe_236, R.raw.recipe_237, R.raw.recipe_238, R.raw.recipe_239, R.raw.recipe_240
                , R.raw.recipe_241, R.raw.recipe_242, R.raw.recipe_243, R.raw.recipe_244, R.raw.recipe_245
                , R.raw.recipe_246, R.raw.recipe_247, R.raw.recipe_248, R.raw.recipe_249, R.raw.recipe_250
                , R.raw.recipe_251, R.raw.recipe_252, R.raw.recipe_253, R.raw.recipe_254, R.raw.recipe_255
                , R.raw.recipe_256, R.raw.recipe_257, R.raw.recipe_258, R.raw.recipe_259, R.raw.recipe_260
                , R.raw.recipe_261, R.raw.recipe_262, R.raw.recipe_263, R.raw.recipe_264, R.raw.recipe_265
                , R.raw.recipe_266, R.raw.recipe_267, R.raw.recipe_268, R.raw.recipe_269, R.raw.recipe_270
                , R.raw.recipe_271, R.raw.recipe_272, R.raw.recipe_273, R.raw.recipe_274, R.raw.recipe_275
                , R.raw.recipe_276, R.raw.recipe_277, R.raw.recipe_278, R.raw.recipe_279, R.raw.recipe_280
                , R.raw.recipe_281, R.raw.recipe_282, R.raw.recipe_283, R.raw.recipe_284, R.raw.recipe_285
                , R.raw.recipe_286, R.raw.recipe_287, R.raw.recipe_288, R.raw.recipe_289, R.raw.recipe_290
                , R.raw.recipe_291, R.raw.recipe_292, R.raw.recipe_293, R.raw.recipe_294, R.raw.recipe_295
                , R.raw.recipe_296, R.raw.recipe_297, R.raw.recipe_298, R.raw.recipe_299, R.raw.recipe_300
                , R.raw.recipe_301, R.raw.recipe_302, R.raw.recipe_303
        };
        String filePath = getFilesDir().getPath() + File.separator;
        for (int i = 1; i <= 303; i++) {
            String imagePath = filePath + "recipe_" + i + ".jpg";
            FileUtils.writeData(this, imagePath, imageId[i - 1]);
        }
        FileUtils.writeData(this, filePath + "share.png", R.raw.img_share);
        String dbPath = getDir(Const.DB_DIR, MODE_PRIVATE) + File.separator + Const.DB_NAME;
        FileUtils.writeData(SplashActivity.this, dbPath, R.raw.cookbook);
    }

    private class FileTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            init();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startMain();
        }
    }
}