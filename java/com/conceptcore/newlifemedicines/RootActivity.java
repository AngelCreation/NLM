package com.conceptcore.newlifemedicines;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.Fragments.AddAddressFragment;
import com.conceptcore.newlifemedicines.Fragments.CategoryFragment;
import com.conceptcore.newlifemedicines.Fragments.ChangePasswordFragment;
import com.conceptcore.newlifemedicines.Fragments.FilterFragment;
import com.conceptcore.newlifemedicines.Fragments.MyOrdersFragment;
import com.conceptcore.newlifemedicines.Fragments.OrderDetailsFragment;
import com.conceptcore.newlifemedicines.Fragments.PassbookFragment;
import com.conceptcore.newlifemedicines.Fragments.ProductFragment;
import com.conceptcore.newlifemedicines.Fragments.EditProfileFragment;
import com.conceptcore.newlifemedicines.Fragments.SearchedProductFragment;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Models.OrderDetails;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;

import java.util.List;

public class RootActivity extends AppCompatActivity {

    private String LOG = "HomeActivity:: ";

    private DrawerLayout drawerLayout;
    private ImageView imageviewHome, drawerLogo,cart, imageviewEditProfile,imageviewPassbook, imageviewChangePass,imageviewMyOrders, imageviewAddAddress, imageviewRefer, imageviewLogout;
    private LinearLayout linearLayoutHome, linearLayoutChangePass,linearLayoutMyOrders,linearLayoutPassbook, linearLayoutAddAddress, linearLayoutRefer, linearLayoutLogOut, linearLayoutEditProfile;
    private TextView textViewHome, textViewLogout,textViewPassbook, textViewEditProfile, textViewChangePass,textViewMyOrders,textViewAddAddress, textViewRefer, txtUserName;
    private TextView txtName,txtEmail,txtBalance;
    private SharedPrefBean sharedPrefBean;

    private String userId;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        System.gc();

        sharedPrefBean = new SharedPrefBean();
        sharedPrefBean = getLocalSharedPreferences();
        userId = sharedPrefBean.getUserId();

        if (userId.equals("0") || userId.equals(null)) {
            goToLogin();
            return;
        }

        //find views
        drawerLayout = findViewById(R.id.drawerLayout);
        linearLayoutHome = findViewById(R.id.linearLayoutHome);
        linearLayoutRefer = findViewById(R.id.linearLayoutRefer);
        linearLayoutAddAddress = findViewById(R.id.linearLayoutAddAddress);
        linearLayoutChangePass = findViewById(R.id.linearLayoutChangePass);
        linearLayoutMyOrders = findViewById(R.id.linearLayoutMyOrders);
        linearLayoutPassbook = findViewById(R.id.linearLayoutPassbook);
        linearLayoutLogOut = findViewById(R.id.linearLayoutLogOut);
        linearLayoutEditProfile = findViewById(R.id.linearLayoutEditProfile);

        imageviewHome = findViewById(R.id.imageviewHome);
        imageviewEditProfile = findViewById(R.id.imageviewEditProfile);
        imageviewRefer = findViewById(R.id.imageviewRefer);
        imageviewAddAddress = findViewById(R.id.imageviewAddAddress);
        imageviewMyOrders = findViewById(R.id.imageviewMyOrders);
        imageviewPassbook = findViewById(R.id.imageviewPassbook);
        imageviewChangePass = findViewById(R.id.imageviewChangePass);
        imageviewLogout = findViewById(R.id.imageviewLogout);

        textViewHome = findViewById(R.id.textViewHome);
        textViewEditProfile = findViewById(R.id.textViewEditProfile);
        textViewRefer = findViewById(R.id.textViewRefer);
        textViewMyOrders = findViewById(R.id.textViewMyOrders);
        textViewPassbook = findViewById(R.id.textViewPassbook);
        textViewAddAddress = findViewById(R.id.textViewAddAddress);
        textViewChangePass = findViewById(R.id.textViewChangePass);
        textViewLogout = findViewById(R.id.textViewLogout);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtBalance = findViewById(R.id.txtBalance);

//        searchView = findViewById(R.id.searchView);

        setHomeSelected();

        setNavigationDrawer();

        if(getIntent().getExtras() != null){
            boolean fromCheckOut = getIntent().getExtras().getBoolean("fromCheckOut");
            if(fromCheckOut){
                openMyOrders();
            }
        } else {
            openCategory();
        }



    }

    private void setNavigationDrawer() {
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                hideKeyBoard(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        drawerLogo = findViewById(R.id.drawerLogo);
        cart = findViewById(R.id.cart);

        drawerLogo.requestFocus();

        drawerLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
                hideKeyBoard(view);
                txtName.setText(sharedPrefBean.getFirstName() + " " + sharedPrefBean.getLastName());
                txtEmail.setText(sharedPrefBean.getEmail());
                txtBalance.setText(sharedPrefBean.getWalletBalance());
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard(view);
                goToCart();
            }
        });

        linearLayoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setHomeSelected();

                drawerLayout.closeDrawers();

                openCategory();
            }
        });

        linearLayoutEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setProfileSelected();

                drawerLayout.closeDrawers();
                openEditProfile();

            }
        });

        linearLayoutAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddAddressSelected();
                drawerLayout.closeDrawers();
                openAddAddress();
            }
        });

        linearLayoutMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyOrdersSelected();
                drawerLayout.closeDrawers();
                openMyOrders();
            }
        });

        linearLayoutPassbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassbookSelected();
                drawerLayout.closeDrawers();
                openPassbook();
            }
        });


        linearLayoutChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChangePassSelected();
                drawerLayout.closeDrawers();
                openChangePass();
            }
        });

        linearLayoutRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // setReferSelected();

                drawerLayout.closeDrawers();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                try {
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey I came across this awesome App! Download Now. https://play.google.com/store/apps/details?id=com.conceptcore.newlifemedical");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendIntent.setType("text/plain");

                PackageManager pm = getPackageManager();
                List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);

                for (int i = 0; i < resInfo.size(); i++) {
                    ResolveInfo ri = resInfo.get(i);
                    String packageName = ri.activityInfo.packageName;

                    if (packageName.contains("android.email") || packageName.contains("android.gm")) {
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "New Life Medical Application");
                    }
                }
                startActivity(sendIntent);
            }
        });

        linearLayoutLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // setLogOutSelected();

                drawerLayout.closeDrawers();

                //do change
//                updateTokenLogout(userId, 0);

                clearSharedPreferences();
            }
        });
    }

    private void setHomeSelected() {
        linearLayoutHome.setBackgroundColor(ContextCompat.getColor(RootActivity.this, R.color.colorPrimary));
        linearLayoutEditProfile.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutAddAddress.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutMyOrders.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutPassbook.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutChangePass.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutRefer.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutLogOut.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));

        textViewHome.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.white));
        textViewEditProfile.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewAddAddress.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewMyOrders.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewPassbook.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewChangePass.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewRefer.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewLogout.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));

        imageviewHome.setImageResource(R.drawable.ic_nav_home_white);
        imageviewEditProfile.setImageResource(R.drawable.ic_nav_profile);
        imageviewAddAddress.setImageResource(R.drawable.ic_add_address);
        imageviewMyOrders.setImageResource(R.drawable.ic_nav_cart);
        imageviewPassbook.setImageResource(R.drawable.ic_nav_passbook);
        imageviewChangePass.setImageResource(R.drawable.ic_password);
        imageviewRefer.setImageResource(R.drawable.ic_nav_refer);
        imageviewLogout.setImageResource(R.drawable.ic_nav_logout);
    }

    private void setProfileSelected() {
        linearLayoutHome.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutEditProfile.setBackgroundColor(ContextCompat.getColor(RootActivity.this, R.color.colorPrimary));
        linearLayoutAddAddress.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutMyOrders.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutPassbook.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutChangePass.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutRefer.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutLogOut.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));

        textViewHome.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewEditProfile.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.white));
        textViewAddAddress.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewMyOrders.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewPassbook.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewChangePass.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewRefer.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewLogout.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));

        imageviewHome.setImageResource(R.drawable.ic_nav_home);
        imageviewEditProfile.setImageResource(R.drawable.ic_nav_profile_white);
        imageviewAddAddress.setImageResource(R.drawable.ic_add_address);
        imageviewMyOrders.setImageResource(R.drawable.ic_nav_cart);
        imageviewPassbook.setImageResource(R.drawable.ic_nav_passbook);
        imageviewChangePass.setImageResource(R.drawable.ic_password);
        imageviewRefer.setImageResource(R.drawable.ic_nav_refer);
        imageviewLogout.setImageResource(R.drawable.ic_nav_logout);
    }

    private void setAddAddressSelected() {
        linearLayoutHome.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutEditProfile.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutAddAddress.setBackgroundColor(ContextCompat.getColor(RootActivity.this, R.color.colorPrimary));
        linearLayoutMyOrders.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutPassbook.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutChangePass.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutRefer.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutLogOut.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));

        textViewHome.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewEditProfile.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewAddAddress.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.white));
        textViewMyOrders.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewPassbook.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewChangePass.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewRefer.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewLogout.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));

        imageviewHome.setImageResource(R.drawable.ic_nav_home);
        imageviewEditProfile.setImageResource(R.drawable.ic_nav_profile);
        imageviewAddAddress.setImageResource(R.drawable.ic_add_address_white);
        imageviewMyOrders.setImageResource(R.drawable.ic_nav_cart);
        imageviewPassbook.setImageResource(R.drawable.ic_nav_passbook);
        imageviewChangePass.setImageResource(R.drawable.ic_password);
        imageviewRefer.setImageResource(R.drawable.ic_nav_refer);
        imageviewLogout.setImageResource(R.drawable.ic_nav_logout);
    }

    private void setMyOrdersSelected() {
        linearLayoutHome.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutEditProfile.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutAddAddress.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutMyOrders.setBackgroundColor(ContextCompat.getColor(RootActivity.this, R.color.colorPrimary));
        linearLayoutPassbook.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutChangePass.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutRefer.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutLogOut.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));

        textViewHome.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewEditProfile.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewAddAddress.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewMyOrders.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.white));
        textViewPassbook.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewChangePass.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewRefer.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewLogout.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));

        imageviewHome.setImageResource(R.drawable.ic_nav_home);
        imageviewEditProfile.setImageResource(R.drawable.ic_nav_profile);
        imageviewAddAddress.setImageResource(R.drawable.ic_add_address);
        imageviewMyOrders.setImageResource(R.drawable.ic_nav_cart_white);
        imageviewPassbook.setImageResource(R.drawable.ic_nav_passbook);
        imageviewChangePass.setImageResource(R.drawable.ic_password);
        imageviewRefer.setImageResource(R.drawable.ic_nav_refer);
        imageviewLogout.setImageResource(R.drawable.ic_nav_logout);
    }

    private void setPassbookSelected() {
        linearLayoutHome.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutEditProfile.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutAddAddress.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutMyOrders.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutPassbook.setBackgroundColor(ContextCompat.getColor(RootActivity.this, R.color.colorPrimary));
        linearLayoutChangePass.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutRefer.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutLogOut.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));

        textViewHome.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewEditProfile.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewAddAddress.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewMyOrders.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewPassbook.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.white));
        textViewChangePass.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewRefer.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewLogout.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));

        imageviewHome.setImageResource(R.drawable.ic_nav_home);
        imageviewEditProfile.setImageResource(R.drawable.ic_nav_profile);
        imageviewAddAddress.setImageResource(R.drawable.ic_add_address);
        imageviewMyOrders.setImageResource(R.drawable.ic_nav_cart);
        imageviewPassbook.setImageResource(R.drawable.ic_nav_passbook_white);
        imageviewChangePass.setImageResource(R.drawable.ic_password);
        imageviewRefer.setImageResource(R.drawable.ic_nav_refer);
        imageviewLogout.setImageResource(R.drawable.ic_nav_logout);
    }

    private void setChangePassSelected(){
        linearLayoutHome.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutEditProfile.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutAddAddress.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutMyOrders.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutPassbook.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutChangePass.setBackgroundColor(ContextCompat.getColor(RootActivity.this, R.color.colorPrimary));
        linearLayoutRefer.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));
        linearLayoutLogOut.setBackgroundColor(ContextCompat.getColor(RootActivity.this, android.R.color.transparent));

        textViewHome.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewEditProfile.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewAddAddress.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewMyOrders.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewPassbook.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewChangePass.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.white));
        textViewRefer.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));
        textViewLogout.setTextColor(ContextCompat.getColor(RootActivity.this, android.R.color.black));

        imageviewHome.setImageResource(R.drawable.ic_nav_home);
        imageviewEditProfile.setImageResource(R.drawable.ic_nav_profile);
        imageviewAddAddress.setImageResource(R.drawable.ic_add_address);
        imageviewMyOrders.setImageResource(R.drawable.ic_nav_cart);
        imageviewPassbook.setImageResource(R.drawable.ic_nav_passbook);
        imageviewChangePass.setImageResource(R.drawable.ic_password_white);
        imageviewRefer.setImageResource(R.drawable.ic_nav_refer);
        imageviewLogout.setImageResource(R.drawable.ic_nav_logout);
    }

    //shared preferences methods
    private SharedPrefBean getLocalSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        sharedPrefBean.setUserId(prefs.getString("userId", "0"));
        sharedPrefBean.setEmail(prefs.getString("email",""));
        sharedPrefBean.setFirstName(prefs.getString("firstName","User"));
        sharedPrefBean.setLastName(prefs.getString("lastName",""));
        sharedPrefBean.setWalletBalance(prefs.getString("walletBalance", "0"));

        return sharedPrefBean;
    }

    private void clearSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
//        LoginManager.getInstance().logOut(); //for fb logout
        goToLogin();
    }

    private void goToCart(){
        Intent intent = new Intent(this, ShoppingCart.class);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        if(fragment instanceof SearchedProductFragment){
            intent.putExtra("fromProduct",true);
        } else{
            intent.putExtra("fromProduct",false);
        }
        startActivity(intent);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LogIn.class);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        startActivity(intent);
        finish();
    }

    private void openCategory() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CategoryFragment()).commit();
    }

    private void openEditProfile(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new EditProfileFragment()).commit();
    }

    private void openAddAddress(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new AddAddressFragment()).commit();
    }

    private void openMyOrders(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new MyOrdersFragment()).commit();
    }

    private void openPassbook(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new PassbookFragment()).commit();
    }

    private void openChangePass(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new ChangePasswordFragment()).commit();
    }

    private void hideKeyBoard(View view) {
        //close keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
        } else if (fragment instanceof ProductFragment) {
            openCategory();
        } else if (fragment instanceof ChangePasswordFragment) {
            openCategory();
        } else if (fragment instanceof EditProfileFragment) {
            openCategory();
        } else if (fragment instanceof AddAddressFragment) {
            openCategory();
        } else if (fragment instanceof SearchedProductFragment) {
            openCategory();
        } else if (fragment instanceof MyOrdersFragment) {
            openCategory();
        } else if (fragment instanceof PassbookFragment) {
            openCategory();
        } /*else if (fragment instanceof OrderDetailsFragment) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new MyOrdersFragment()).commit();

        }*/ else if (fragment instanceof CategoryFragment  ) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(RootActivity.this);
            builder1.setTitle("Please Confirm!");
            builder1.setMessage("Are you sure to Exit?");

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                            return;
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }  else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this, "rootactivity onactresult called", Toast.LENGTH_SHORT).show();

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
