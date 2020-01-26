package com.example.deltahacks;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;


public class AR extends AppCompatActivity {
    private RadioGroup radioAnsGroup;
    private RadioButton radioButton;
    private Button enterButtonDisplay;

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }

        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later",
                    Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }

        return true;
    }

    private static final String TAG = AR.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    ArFragment arFragment;
    ModelRenderable catRenderable;
    ViewRenderable u_answerRenderable;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        // Creating renderables
        setContentView(R.layout.activity_ar);

//        addListenerOnButton();
//        radioAnsGroup = (RadioGroup) findViewById(R.id.radioGroup);
//        enterButtonDisplay = (Button) findViewById(R.id.submitButton);
//
//        enterButtonDisplay.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                int radioSelectedId = radioAnsGroup.getCheckedRadioButtonId();
//                radioButton = (RadioButton) findViewById(radioSelectedId);
//                // Correct answer
//                RadioButton radioAnswer = findViewById(R.id.radioButton2);
//
//                if (radioButton == radioAnswer)
//                    Toast.makeText(AR.this,
//                            "Correct!", Toast.LENGTH_SHORT).show();
//            }
//        });

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);
        ModelRenderable.builder()
                .setSource(this, Uri.parse("Mesh_Cat.sfb"))
                .build()
                .thenAccept(renderable -> catRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast = Toast.makeText(this, "Unable to load renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ViewRenderable.builder()
                .setView(this, R.layout.user_answer)
                .build()
                .thenAccept(renderable -> u_answerRenderable = renderable);

        // Adding the model to the scene:
        arFragment.setOnTapArPlaneListener(
                (HitResult hitresult, Plane plane, MotionEvent motionevent) -> {
            if (catRenderable == null || u_answerRenderable == null) {
                    return;
            }

            Anchor anchor = hitresult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());

            TransformableNode cat = new TransformableNode(arFragment.getTransformationSystem());

            cat.getScaleController().setMaxScale(0.4f);
            cat.getScaleController().setMinScale(0.2f);

            cat.setParent(anchorNode);
            cat.setRenderable(catRenderable);
            cat.select();

            TransformableNode u_answer = new TransformableNode(arFragment.getTransformationSystem());

            u_answer.setParent(cat);
            u_answer.setRenderable(u_answerRenderable);
            u_answer.select();
            u_answer.setLocalPosition(new Vector3(0.5f, 0.7f, 2.0f));

        });

    }

//    public void addListenerOnButton() {
//
//        radioAnsGroup = (RadioGroup) findViewById(R.id.radioGroup);
//        enterButtonDisplay = (Button) findViewById(R.id.submitButton);
//
//        enterButtonDisplay.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//                public void onClick(View v) {
//
//            }
//        });


    }



