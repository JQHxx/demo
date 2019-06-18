package wenran.com.baselibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Crowhine on 2019/2/19
 *
 * @author Crowhine
 */
public class GlideUtil {

    /**
     * 通过url设置图片
     */
    public static void setImageByUrl(Context context, String url, ImageView imageView,
                                     Integer placeholder, Integer error) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        if (placeholder != null) {
            requestOptions.placeholder(placeholder);
        }
        if (error != null) {
            requestOptions.placeholder(error);
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 通过url设置圆角图片
     */
    public static void setRoundedCornersImageByUrl(Context context, String url, ImageView imageView
            , int radius, int margin, RoundedCornersTransformation.CornerType cornerType,
                                                   Integer placeholder, Integer error) {
        RequestOptions requestOptions = new RequestOptions();
        RoundedCornersTransformation roundedCornersTransformation =
                new RoundedCornersTransformation(radius, margin, cornerType);
        requestOptions.transforms(new CenterCrop(), roundedCornersTransformation);
        if (placeholder != null) {
            requestOptions.placeholder(placeholder);
        }
        if (error != null) {
            requestOptions.placeholder(error);
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 通过url设置圆形图片
     */
    public static void setCircleImageByUrl(Context context, String url, ImageView imageView,
                                           Integer placeholder, Integer error) {
        RequestOptions requestOptions = new RequestOptions()
                .circleCrop();
        if (placeholder != null) {
            requestOptions.placeholder(placeholder);
        }
        if (error != null) {
            requestOptions.placeholder(error);
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 通过Res设置图片
     */
    public static void setImageByRes(Context context, int res, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        Glide.with(context)
                .load(res)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 通过res设置圆角图片
     */
    public static void setRoundedCornersImageByRes(Context context, int res, ImageView imageView
            , int radius, int margin, RoundedCornersTransformation.CornerType cornerType,
                                                   Integer placeholder, Integer error) {
        RequestOptions requestOptions = new RequestOptions();
        RoundedCornersTransformation roundedCornersTransformation =
                new RoundedCornersTransformation(radius, margin, cornerType);
        requestOptions.transforms(new CenterCrop(), roundedCornersTransformation);
        if (placeholder != null) {
            requestOptions.placeholder(placeholder);
        }
        if (error != null) {
            requestOptions.placeholder(error);
        }
        Glide.with(context)
                .load(res)
                .apply(requestOptions)
                .into(imageView);
    }
}
