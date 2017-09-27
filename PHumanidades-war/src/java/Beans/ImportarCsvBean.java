/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ruben
 */
@ManagedBean
@SessionScoped
public class ImportarCsvBean {

    private UploadedFile file;
    private String fileContent;
    private List<CSVDataFile> dataList;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public List<CSVDataFile> getDataList() {
        return dataList;
    }

    public void setDataList(List<CSVDataFile> dataList) {
        this.dataList = dataList;
    }
    
    
    
    public void validateFile(FacesContext ctx,
                         UIComponent comp,
                         Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        UploadedFile localfile = (UploadedFile)value;
        System.out.println(localfile.getContentType());
        if (localfile.getSize() > 1024) {
            msgs.add(new FacesMessage("Archivo muy grande"));
        }
        if (!"text/csv".equals(localfile.getContentType())) {
            msgs.add(new FacesMessage("Tipo de Archivo no soportado"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }
    
    public List<String> getCSVData(String line){
        List<String> result = new ArrayList<>();
        boolean doblequote = false;
        boolean doblequoteend = false;
        if(line.startsWith("\"")){
            StringBuffer buffer = new StringBuffer();
            char[] chars = line.toCharArray();
            for(char c:  chars){
                
                        if(c == '\"'){
                 
                            continue;
                        }else if(c == ' '){
                            continue;
                        }else if(c == '='){
                            continue;
                        }else if(c == ','){

                            result.add(buffer.toString());
                            buffer = new StringBuffer();
                            continue;
                        }else{
                            buffer.append(c);
                        }
                
            }

            result.add(buffer.toString());
        }
        return result;
    }
    
    public String upload() {
        try {
            dataList = new ArrayList<CSVDataFile>();
            Scanner sc = new Scanner(file.getInputstream());
            while(sc.hasNextLine()){
                fileContent = sc.nextLine().trim();
                List<String> resultLine = getCSVData(fileContent);
                System.out.println(resultLine);
                CSVDataFile df = new CSVDataFile();
                if(!resultLine.isEmpty()){
                    System.out.println(resultLine.get(2));
                    System.out.println(resultLine.get(2).getClass().getSimpleName());
                    System.out.println("un string");
                    df.setConcepto(resultLine.get(0));
                    df.setUsuario(resultLine.get(1));
                    df.setImporte(new BigDecimal(resultLine.get(2)));
                    df.setFecha(new SimpleDateFormat("yyyyMMdd").parse(resultLine.get(3)));
                    df.setReferencia(resultLine.get(4));
                    dataList.add(df);
                }
            }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (ParseException ex) {
            
        }
        return null;
    }
    
    public class CSVDataFile{
        
        String concepto;
        String usuario;
        BigDecimal importe;
        Date fecha;
        String referencia;

        public String getConcepto() {
            return concepto;
        }

        public void setConcepto(String concepto) {
            this.concepto = concepto;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public BigDecimal getImporte() {
            return importe;
        }

        public void setImporte(BigDecimal importe) {
            this.importe = importe;
        }

        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }

        public String getReferencia() {
            return referencia;
        }

        public void setReferencia(String referencia) {
            this.referencia = referencia;
        }
        
        
    }
    
}
