package com.fancon.android.ui.widget;

import android.view.View;
/**
 * Progress for meshtiles
 * @author binhbt
 *
 */
public interface IProgress {
	public void showProgress(String title);
	public void showProgress(View v, String title);
	public void dismissProgress();
}
