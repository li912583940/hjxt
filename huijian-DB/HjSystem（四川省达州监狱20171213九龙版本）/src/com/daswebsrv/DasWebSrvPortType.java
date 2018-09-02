package com.daswebsrv;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2
 * Sun Sep 24 14:30:34 CST 2017
 * Generated source version: 2.2
 * 
 */
 
@WebService(targetNamespace = "http://10.13.248.170:8081/DasWebSrv.wsdl", name = "DasWebSrvPortType")
@XmlSeeAlso({ObjectFactory.class})
public interface DasWebSrvPortType {

    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getCardNo", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.GetCardNo")
    @ResponseWrapper(localName = "getCardNoResponse", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.GetCardNoResponse")
    @WebMethod
    public java.lang.String getCardNo(
        @WebParam(name = "sEmpNo", targetNamespace = "")
        java.lang.String sEmpNo
    );

    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getCardID", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.GetCardID")
    @ResponseWrapper(localName = "getCardIDResponse", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.GetCardIDResponse")
    @WebMethod
    public java.lang.String getCardID(
        @WebParam(name = "sEmpNo", targetNamespace = "")
        java.lang.String sEmpNo
    );

    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getHrByCardNo", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.GetHrByCardNo")
    @ResponseWrapper(localName = "getHrByCardNoResponse", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.GetHrByCardNoResponse")
    @WebMethod
    public java.lang.String getHrByCardNo(
        @WebParam(name = "sCardNo", targetNamespace = "")
        java.lang.String sCardNo
    );

    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "sendMjRecord", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.SendMjRecord")
    @ResponseWrapper(localName = "sendMjRecordResponse", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.SendMjRecordResponse")
    @WebMethod
    public int sendMjRecord(
        @WebParam(name = "sMjRecord", targetNamespace = "")
        java.lang.String sMjRecord
    );

    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "sendHjAppRegInfo", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.SendHjAppRegInfo")
    @ResponseWrapper(localName = "sendHjAppRegInfoResponse", targetNamespace = "urn:DasWebSrv", className = "daswebsrv.SendHjAppRegInfoResponse")
    @WebMethod
    public int sendHjAppRegInfo(
        @WebParam(name = "sRegisterInfo", targetNamespace = "")
        java.lang.String sRegisterInfo
    );
}
