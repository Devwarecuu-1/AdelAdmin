package com.adelnor.adeladmin.utils

object AppConstants {

    //const val URL_MAIN = "http://104.215.117.162/" //ok
    //const val URL_MAIN = "http://192.168.69.230/"
    const val URL_MAIN = ""
    const val URL_LOGIN: String = "api/Login"
    const val URL_USER_ENT: String = "api/Login"

    const val URL_BASIC_CONFIG: String = "api/Comun"
    const val URL_SUPPLIERS: String = "api/GetProveedores"

    //==============================================================================================
    // PROPERTY - ENTRIES
    //==============================================================================================
    const val URL_PROPERTY_01: String = "api/EntradaCompraProveedor"
    const val URL_PROPERTY_01_SAVE: String = "api/PersisteInformacion"
    const val URL_PROPERTY_01_UPDATE: String = "api/ActualizaInformacion"
    const val URL_PROPERTY_01_CANCEL: String = "api/CancelaDocumento"

    const val URL_PROPERTY_02: String = "api/EntradaTransferenciaAlmacen"
    const val URL_PROPERTY_02_LOAD: String = "api/GetPersistenciaETACEntradaTransferenciaAlmacen"
    const val URL_PROPERTY_02_VALIDATE: String = "api/validaFolioSalidaETACEntradaTransferenciaAlmacen"
    const val URL_PROPERTY_02_SAVE: String = "api/PersisteEntradaTransferenciaAlmacen"
    const val URL_PROPERTY_02_UPDATE: String =  "api/ActualizaEntradaTransferenciaAlmacen"
    const val URL_PROPERTY_02_CANCEL: String = "api/CancelaEntradaTransferenciaAlmacen"

    const val URL_PROPERTY_03_VALIDATE: String = "api/ConsultaInformacionSolicitud"
    const val URL_PROPERTY_03_LOAD: String = "api/ConsultaDocumentoDevolucion"
    const val URL_PROPERTY_03_LOAD_DETAILS: String = "api/getDetalleProducto"
    const val URL_PROPERTY_03_SAVE: String = "api/PersisteEntradaDevolucionCliente"
    const val URL_PROPERTY_03_UPDATE: String = "api/ActualizaEntradaDevolucionCliente"
    const val URL_PROPERTY_03_CANCEL: String = "api/CancelaEntradaDevolucionCliente"

    //==============================================================================================
    // PROPERTY - DEPARTURES
    //==============================================================================================
    const val URL_PROPERTY_05: String = "api/SalidaEntregaFisicaCliente"
    const val URL_PROPERTY_05_LOAD_DOC: String = "api/GetFolioLecturaEntregaFisicaCliente"
    const val URL_PROPERTY_05_LOAD: String = "api/GetFolioFacturaSalidaEntregaFisicaCliente"
    const val URL_PROPERTY_05_SAVE: String = "api/PersisteEntregaFisicaCliente"
    const val URL_PROPERTY_05_UPDATE: String = "api/ActualizaEntregaFisicaCliente"

    const val URL_PROPERTY_07: String = "api/SalidaTransferenciaAlmacen"
    const val URL_PROPERTY_07_SAVE: String = "api/PersisteSalidaTransferenciaAlmacen"
    const val URL_PROPERTY_07_UPDATE: String = "api/ActualizaSalidaTransferenciaAlmacen"
    const val URL_PROPERTY_07_CANCEL: String = "api/SalidaTransferenciaAlmacen"

    const val URL_PROPERTY_08: String = "api/SalidaDevolucionProveedor"
    const val URL_PROPERTY_08_SAVE: String = "api/PersisteSalidaDevolucionProveedor"
    const val URL_PROPERTY_08_UPDATE: String = "api/ActualizaSalidaDevolucionProveedor"
    const val URL_PROPERTY_08_CANCEL: String = "api/CancelaSalidaDevolucionProveedor"


    const val URL_PROPERTY_13: String = "api/SalidaReetiquetadoProveedor"
    const val URL_PROPERTY_13_LOAD_DOC: String = "api/GetPersistenciaSRPCSalidaReetiquetadoProveedor"
    const val URL_PROPERTY_13_SAVE: String = "api/PersisteSalidaReetiquetadoProveedor"
    const val URL_PROPERTY_13_UPDATE: String = "api/ActualizaSalidaReetiquetadoProveedor"
    const val URL_PROPERTY_13_CANCEL: String = "api/CancelaSalidaReetiquetadoProveedor"



    //==============================================================================================
    // CONSIGNMENT - ENTRIES
    //==============================================================================================
    const val URL_CONSIGNMENT_01: String = "api/EntradaConsignacionProveedor"
    const val URL_CONSIGNMENT_01_SAVE: String = "api/PersisteEntradaConsignacionProveedor"
    const val URL_CONSIGNMENT_01_UPDATE: String = "api/ActualizaEntradaConsignacionProveedor"
    const val URL_CONSIGNMENT_01_CANCEL: String = "api/CancelaEntradaConsignacionProveedor"

    //==============================================================================================
    //SALIDA TRANSFERENCIA ALMACEN
    //==============================================================================================
    const val URL_PROPERTY_04: String = URL_MAIN + "api/SalidaTransferenciaAlmacen"
    const val URL_PROPERTY_04_UPDATE: String = URL_MAIN + "api/ActualizaSalidaTransferenciaAlmacen"
    const val URL_PROPERTY_04_SAVE: String = URL_MAIN + "api/PersisteSalidaTransferenciaAlmacen"
    const val URL_PROPERTY_04_LOAD_DOC: String = URL_MAIN + "api/SalidaTransferenciaAlmacen"
    const val URL_PROPERTY_04_CANCEL: String = URL_MAIN + "api/CancelaSalidaTransferenciaAlmace"


    //==============================================================================================
    //CAMBIO DE DOMICILIO
    //==============================================================================================
    const val URL_PROPERTY_82: String = URL_MAIN + "api/obtenerdomicilio"
    const val URL_PROPERTY_82_SAVE: String = URL_MAIN + "api/guardarCambioDomicilioAlmacen"
    const val URL_PROPERTY_82_PROD: String = URL_MAIN + "api/AcomodoMercancias"

    //==============================================================================================
    //SALIDA TRANSFERENCIA ALMACEN PROPIO
    //==============================================================================================
    const val URL_PROPERTY_020: String = URL_MAIN + "api/SalidaTransferenciaAlmacenPropio"
    const val URL_PROPERTY_020_UPDATE: String = URL_MAIN + "api/ActualizaSalidaTransferenciaAlmacenPropio"
    const val URL_PROPERTY_020_SAVE: String = URL_MAIN + "api/PersisteSalidaTransferenciaAlmacenPropio"
    const val URL_PROPERTY_020_LOAD_DOC: String = URL_MAIN + "api/SalidaTransferenciaAlmacenPropio"
    const val URL_PROPERTY_020_CANCEL: String = URL_MAIN + "api/CancelaSalidaTransferenciaAlmacenPropio"







}