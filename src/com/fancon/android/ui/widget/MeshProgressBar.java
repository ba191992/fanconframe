package com.fancon.android.ui.widget;
/**
 * Custome progress bar -> smaller and iphone's style
 * @author binhbt
 */
import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fancon.android.R;

public class MeshProgressBar extends Dialog {
	public static MeshProgressBar show(Context context, CharSequence title,
			CharSequence message) {
		return show(context, title, message, false);
	}

	public static MeshProgressBar show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate) {
		return show(context, title, message, indeterminate, false, null);
	}

	public static MeshProgressBar show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable) {
		return show(context, title, message, indeterminate, cancelable, null);
	}

	public static MeshProgressBar show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener) {
		MeshProgressBar dialog = new MeshProgressBar(context);
		dialog.setTitle(title);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		/* The next line will add the ProgressBar to the dialog. */
		dialog.addContentView(new ProgressBar(context), new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		dialog.show();

		return dialog;
	}

	public MeshProgressBar(Context context) {
		//super(context, R.style.mesh_progress);
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
	}
	
	
	public static MeshProgressBar show(Context context, String title) {
		MeshProgressBar dialog = new MeshProgressBar(context, title);
		dialog.show();
		return dialog;
	}
	public MeshProgressBar(Context context, String title) {
		//super(context, R.style.mesh_progress);
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		setContentView(R.layout.common_mesh_progress_bar);
		TextView message = (TextView) findViewById(R.id.progress_text);
		message.setText(title);
	}
}
