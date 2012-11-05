package com.fancon.android.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
/**
 * FragmentUtil for navigate screen between fragment
 * @author Binhbt
 * @2012
 */
public class FragmentUtil {
	public static final String ARGUMENTS_KEY_NO_BACK_STACK = "noBackStack";

	public static void detachAndAdd(FragmentManager paramFragmentManager,
			Fragment paramFragment1, Fragment paramFragment2, Bundle paramBundle) {
		FragmentTransaction localFragmentTransaction = paramFragmentManager
				.beginTransaction();
		paramFragment2.setArguments(paramBundle);
		localFragmentTransaction.detach(paramFragment1);
		localFragmentTransaction.add(android.R.id.content, paramFragment2);
		localFragmentTransaction.commit();
		paramFragmentManager.executePendingTransactions();
	}

//	public static void navigateTo(FragmentManager paramFragmentManager,
//			Fragment paramFragment, Bundle paramBundle) {
//		FragmentTransaction localFragmentTransaction = paramFragmentManager
//				.beginTransaction();
//		if ((paramBundle != null)
//				&& (paramBundle.getBoolean(ARGUMENTS_KEY_NO_BACK_STACK)))
//			paramBundle.remove(ARGUMENTS_KEY_NO_BACK_STACK);
//		paramFragment.setArguments(paramBundle);
//		localFragmentTransaction.replace(android.R.id.content, paramFragment);
//		localFragmentTransaction.commit();
//		paramFragmentManager.executePendingTransactions();
//		localFragmentTransaction.addToBackStack(null);
//	}

	public static void navigateTo(FragmentManager paramFragmentManager,
			Fragment paramFragment, String paramString, Bundle paramBundle) {
		FragmentTransaction localFragmentTransaction = paramFragmentManager
				.beginTransaction();
		paramFragment.setArguments(paramBundle);
		localFragmentTransaction.replace(android.R.id.content, paramFragment);
		// @fade
//		localFragmentTransaction
//				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		localFragmentTransaction.addToBackStack(paramString);
		localFragmentTransaction.commit();
		paramFragmentManager.executePendingTransactions();
	}
//	public static void navigateNoBackStack(FragmentManager paramFragmentManager,
//			Fragment paramFragment, Bundle paramBundle) {
//		FragmentTransaction localFragmentTransaction = paramFragmentManager
//				.beginTransaction();
//		paramFragment.setArguments(paramBundle);
//		localFragmentTransaction.replace(android.R.id.content, paramFragment);
//		localFragmentTransaction.commit();
//		paramFragmentManager.executePendingTransactions();
//	}

	public static void navigateToInNewActivity(Activity paramActivity,
			Fragment paramFragment, Bundle paramBundle, String paramString) {
		// Intent localIntent = new Intent(paramActivity,
		// ArbitraryFragmentActivity.class);
		// localIntent.putExtra("com.instagram.android.activity.ActivityInTab.EXTRA_PREVIOUS_SCREEN_NAME",
		// paramString);
		// localIntent.putExtra("com.instagram.android.activity.ArbitraryFragmentActivity.EXTRAS_FRAGMENT_CLASS_NAME",
		// paramFragment.getClass().getName());
		// localIntent.putExtra("com.instagram.android.activity.ArbitraryFragmentActivity.EXTRAS_BUNDLE",
		// paramBundle);
		// paramActivity.startActivity(localIntent);
	}

	public static void removeAndAdd(FragmentManager paramFragmentManager,
			Fragment paramFragment1, Fragment paramFragment2, Bundle paramBundle) {
		FragmentTransaction localFragmentTransaction = paramFragmentManager
				.beginTransaction();
		paramFragment2.setArguments(paramBundle);
		localFragmentTransaction.remove(paramFragment1);
		localFragmentTransaction.add(android.R.id.content, paramFragment2);
		localFragmentTransaction.commit();
		paramFragmentManager.executePendingTransactions();
	}

	public static void replaceFragment(FragmentManager paramFragmentManager,
			Fragment paramFragment, Bundle paramBundle) {
		FragmentTransaction localFragmentTransaction = paramFragmentManager
				.beginTransaction();
		paramFragment.setArguments(paramBundle);
		localFragmentTransaction.replace(android.R.id.content, paramFragment);
		localFragmentTransaction.commit();
		paramFragmentManager.executePendingTransactions();
	}
}