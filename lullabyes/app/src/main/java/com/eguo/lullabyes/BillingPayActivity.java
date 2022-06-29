package com.eguo.lullabyes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingPayActivity extends AppCompatActivity {
    private BillingClient billingClient;
    private Map<String, SkuDetails> mSkuDetailsMap = new HashMap<>();
    private String mSkuId = "3_sub_taile";
    Button billing2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_pay);
billing2  = (Button) findViewById(R.id.billding2);
        billing2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Error pay " , Toast.LENGTH_LONG).show();
                launchBilling(mSkuId);
            }
        });


        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK &&list !=null) {
                    // The BillingClient is ready. You can query purchases here.
                    payComplete();
                }
            }}).build();

        billingClient.startConnection(new BillingClientStateListener() {


            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.

                    querySkuDetails(); //запрос о товарах
                    List<Purchase> purchasesList = queryPurchases(); //запрос о покупках

                    //если товар уже куплен, предоставить его пользователю
                    for (int i = 0; i < purchasesList.size(); i++) {
                        String purchaseId = purchasesList.get(i).getSku();
                        if(TextUtils.equals(mSkuId, purchaseId)) {
                            payComplete();
                        }
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                //сюда мы попадем если что-то пойдет не так
            }
        });



    }
    private void querySkuDetails() {
        SkuDetailsParams.Builder skuDetailsParamsBuilder = SkuDetailsParams.newBuilder();
        List<String> skuList = new ArrayList<>();
        skuList.add(mSkuId);
        skuDetailsParamsBuilder.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(skuDetailsParamsBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list) {

                    for (SkuDetails skuDetails : list) {
                        mSkuDetailsMap.put(skuDetails.getSku(), skuDetails);
                    }

            }

        });
    }
    public void launchBilling(String skuId) {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(mSkuDetailsMap.get(skuId))
                .build();
        billingClient.launchBillingFlow(this, billingFlowParams);
    }
     public void  payComplete() {

    }
    private List<Purchase> queryPurchases() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
        return purchasesResult.getPurchasesList();
    }

}
