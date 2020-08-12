package com.adelnor.adeladmin.utils

import android.app.Activity
import android.content.Context
import android.net.wifi.WifiManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.adelnor.adeladmin.R
import com.adelnor.adeladmin.model.db.*
import kotlinx.android.synthetic.main.dialog_product_full_info.view.*


class CommonTools {

    fun getLoadingDialog(context: Context): AlertDialog {
        var refAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.loading_banner, null)
        refAlertDialog.setView(view)
        refAlertDialog.setCancelable(false)
        return refAlertDialog.create()
    }

    fun getEntryProductDetailsDialog(item: ItemExtended, context: Context): AlertDialog {
        var refAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_product_full_info, null)
        view.layout_qr.visibility = GONE
        view.text_codprod.text = "${item.intProductId}"
        view.text_product.text = item.presentation
        view.text_codpres.text = "${item.presentationId}"
        view.text_description.text = item.description
        view.text_quantity.text = "${item.quantity}"
        view.text_type.text = "${item.type}"
        view.text_lot.text = item.lot
        view.text_expiration_date.text = item.expirationDate
        view.text_elaboration_date.text = item.elaborationDate
        refAlertDialog.setView(view)
        return refAlertDialog.create()
    }

    fun getOutProductDetailsDialog(item: OutItemExtended, context: Context): AlertDialog {
        var refAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_product_full_info, null)
        view.text_qr_code.text = "${item.qrId}"
        view.text_codprod.text = "${item.intProductId}"
        view.text_product.text = item.presentation
        view.text_codpres.text = "${item.presentationId}"
        view.text_description.text = item.description
        view.text_quantity.text = "${item.quantity}"
        view.text_type.text = "${item.type}"
        view.text_lot.text = item.lot
        view.text_expiration_date.text = item.expirationDate
        view.text_elaboration_date.text = item.elaborationDate

        refAlertDialog.setView(view)
        return refAlertDialog.create()
    }

    fun getSupplierReTagItemDetails(item: SupplierReTagItem, context: Context): AlertDialog {
        var refAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_product_full_info, null)
        view.text_qr_code.text = "${item.qrId}"
        view.text_codprod.text = "${item.intProductId}"
        view.text_product.text = item.presentation
        view.text_codpres.text = "${item.presentationId}"
        view.text_description.text = item.description
        view.text_quantity.text = "${item.quantity}"
        view.text_type.text = "${item.type}"
        view.text_lot.text = item.lot
        view.text_expiration_date.text = item.expirationDate
        view.text_elaboration_date.text = item.elaborationDate
        view.text_cofepris_date.text = "NA"

        refAlertDialog.setView(view)
        return refAlertDialog.create()
    }

    fun getWarehouseTransferItemDetails(item: WarehouseTransferItem, context: Context): AlertDialog {
        var refAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_product_full_info, null)
        view.text_qr_code.text = "${item.qrId}"
        view.text_codprod.text = "${item.intProductId}"
        view.text_product.text = item.presentation
        view.text_codpres.text = "${item.presentationId}"
        view.text_description.text = item.description
        view.text_quantity.text = "${item.quantity}"
        view.text_type.text = "${item.type}"
        view.text_lot.text = item.lot
        view.text_expiration_date.text = item.expirationDate
        view.text_elaboration_date.text = item.elaborationDate
        view.text_cofepris_date.text = "NA"

        refAlertDialog.setView(view)
        return refAlertDialog.create()
    }

    fun getDepartureWarehouseTransferItemDetails(item: DepartureWarehouseTransferItem, context: Context): AlertDialog {
        var refAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_product_full_info, null)
        view.text_qr_code.text = "${item.qrId}"
        view.text_codprod.text = "${item.intProductId}"
        view.text_product.text = item.presentation
        view.text_codpres.text = "${item.presentationId}"
        view.text_description.text = item.description
        view.text_quantity.text = "${item.quantity}"
        view.text_type.text = "${item.type}"
        view.text_lot.text = item.lot
        view.text_expiration_date.text = item.expirationDate
        view.text_elaboration_date.text = item.elaborationDate
        view.text_cofepris_date.text = "NA"

        refAlertDialog.setView(view)
        return refAlertDialog.create()
    }
    fun getMerchandise(item: MerchandiseArraItem, context: Context): AlertDialog {
        var refAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_product_full_info, null)
        view.text_qr_code.text = "${item.niDQR}"
        view.text_codprod.text = "${item.intProductCod}"
        view.text_product.text = item.cDescr
        view.text_codpres.text = "${item.presentationId}"
        view.text_description.text = item.description
        view.text_quantity.text = "${item.quantity}"
        view.text_type.text = "${item.cTipo}"
        view.text_lot.text = item.lot
        view.text_expiration_date.text = item.expirationDate
        view.text_elaboration_date.text = item.elaborationDate
        view.text_cofepris_date.text = "NA"

        refAlertDialog.setView(view)
        return refAlertDialog.create()
    }
    fun getDeapTransferItem(item: DeapTransferItemPost, context: Context): AlertDialog {
        var refAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_product_full_info, null)
        view.text_qr_code.text = "${item.niDQR}"
        view.text_codprod.text = "${item.codProd}"
        view.text_product.text = item.type
        view.text_codpres.text = "${item.presentationId}"
        view.text_description.text = item.description
        view.text_quantity.text = "${item.quantity}"
        view.text_type.text = "${item.type}"
        view.text_lot.text = item.lot
        view.text_expiration_date.text = item.expirationDate
        view.text_elaboration_date.text = item.elaborationDate
        view.text_cofepris_date.text = "NA"

        refAlertDialog.setView(view)
        return refAlertDialog.create()
    }

    fun getPurchaseReturnItemDetails(item: PurchaseReturnItem, context: Context): AlertDialog {
        var refAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_product_full_info, null)
        view.text_qr_code.text = "${item.qrId}"
        view.text_codprod.text = "${item.intProductId}"
        view.text_product.text = item.presentation
        view.text_codpres.text = "${item.presentationId}"
        view.text_description.text = item.description
        view.text_quantity.text = "${item.quantity}"
        view.text_type.text = "${item.type}"
        view.text_lot.text = item.lot
        view.text_expiration_date.text = item.expirationDate
        view.text_elaboration_date.text = item.elaborationDate
        view.text_cofepris_date.text = "NA"

        refAlertDialog.setView(view)
        return refAlertDialog.create()
    }

    fun cleanScannerReaderV1(result: String): String{
        return result.replace("nIdQR:", "")
            .replace("CodProducto:", "")
            .replace("Presentacion:", "")
            .replace("Cantidad:", "")
            .replace("cLote:", "")
            .replace("FechaCaducidad:", "")
            .replace("FechaElaboracion:", "")
    }

    fun cleanScannerReaderV2(result: String): String{
        return result.replace("Producto:", "")
            .replace("Presentacion:", "")
            .replace("FolioDeOrdenDeCompra:", "")
            .replace("FechaCaducidad:", "")
            .replace("FechaElaboracion:", "")
            .replace("Lote:", "")
            .replace("Cantidad:", "")
    }

    fun showDocumentSaveConfirmation(capturedFolio: String, activity: Activity){
        val builder = AlertDialog.Builder(activity)
            .setTitle("Captura Correcta!")
            .setMessage("Captura Registrada correctamente con el folio $capturedFolio")
        builder.setNeutralButton(R.string.btn_accept) { dialog, which ->
            dialog.dismiss()
            activity.finish()
        }
        builder.show()
    }

    fun showCaptureError(context: Context){
        val builder = AlertDialog.Builder(context)
            .setTitle("Error de Guardado!")
            .setMessage("Error al registrar captura")
        builder.setNeutralButton(R.string.btn_accept) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun showFolioInput(context: Context): AlertDialog {
        var folioAlertDialog = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_folio_capture, null)
        folioAlertDialog.setView(view)
        return folioAlertDialog.create()
    }

    fun showMessageDialog(context: Context, title: String, message: String){
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
        builder.setNeutralButton(R.string.btn_accept) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun showEndMessage(message: String, activity: Activity){
        val builder = AlertDialog.Builder(activity)
            .setTitle("Atencion!")
            .setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.btn_accept) { dialog, which ->
            dialog.dismiss()
            activity.finish()
        }
        builder.show()
    }

    fun hideKeyBoard(activity: Activity){
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View = activity.currentFocus!!
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getAppVersion(context: Context): String {
        var pm = context.getPackageManager();
        var pi = pm.getPackageInfo(context.getPackageName(), 0);
        return pi.versionName;
    }

    fun getMacAddress(context: Context): String{
        val wInfo = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wInfo.connectionInfo.macAddress
    }

}