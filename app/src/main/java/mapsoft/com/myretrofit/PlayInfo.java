package mapsoft.com.myretrofit;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Even_Kwok on 2016/9/21.
 */
@Root(name = "item")
public class PlayInfo {

    @Attribute(required = false)
    private int pdid;         //编号(自动生成)
    @Attribute(required = false)
    private int piid;        // 对应节目单表中的编号(自动生成)
    @Attribute(required = false)
    private String piname;        //节目单主表名称
    @Attribute(required = false)
    private String pdtitle;             //文件显示名称（用于显示报表中）
    @Attribute(required = false)
    private String pdfilename;        //文件名称(不含文件路径)
    @Attribute(required = false)
    private String pdpath;        //相对路径位置(含文件名称的相对路径(相对当前EXE(web.config)))
    @Attribute(required = false)
    private String pdfromd;        //播放起始日期
    @Attribute(required = false)
    private String pdtod;            //截止日期
    @Attribute(required = false)
    private int pdmtimesday;    //每天最多播放次数
    @Attribute(required = false)
    private int pdkind;        //信息类别（0 全部，1视频，2图片，3文本
    @Attribute(required = false)
    private String pdtxtc;            //文本内容
    @Attribute(required = false)
    private int pdtxtfsize;        //文本字号
    @Attribute(required = false)
    private int pdseq;        //排序号（文件播放的顺序）
    @Attribute(required = false)
    private int pdkeepl;        //一次显示持续时长，单位秒,（仅对文本、图片）有效
    @Attribute(required = false)
    private String pdfromt;        ////播放起始时间
    @Attribute(required = false)
    private String pdtot;            //-截止时间
    @Attribute(required = false)
    private int pdinvalid;        //作废标志(1为已经作废,0正常)
    @Attribute(required = false)
    private String latmodi;        //最后修改时间(站牌端将根据这个时间与其同步)
    @Attribute(required = false)
    private int ciid;            //所属客户ID
    @Attribute(required = false)
    private String ciname;        //客户名称
    @Attribute(required = false)
    private boolean exist;            //路径是否存在

    public int getPdid() {
        return pdid;
    }

    public void setPdid(int pdid) {
        this.pdid = pdid;
    }

    public int getPiid() {
        return piid;
    }

    public void setPiid(int piid) {
        this.piid = piid;
    }

    public String getPiname() {
        return piname;
    }

    public void setPiname(String piname) {
        this.piname = piname;
    }

    public String getPdtitle() {
        return pdtitle;
    }

    public void setPdtitle(String pdtitle) {
        this.pdtitle = pdtitle;
    }

    public String getPdfilename() {
        return pdfilename;
    }

    public void setPdfilename(String pdfilename) {
        this.pdfilename = pdfilename;
    }

    public String getPdpath() {
        return pdpath;
    }

    public void setPdpath(String pdpath) {
        this.pdpath = pdpath;
    }

    public String getPdfromd() {
        return pdfromd;
    }

    public void setPdfromd(String pdfromd) {
        this.pdfromd = pdfromd;
    }

    public String getPdtod() {
        return pdtod;
    }

    public void setPdtod(String pdtod) {
        this.pdtod = pdtod;
    }

    public int getPdmtimesday() {
        return pdmtimesday;
    }

    public void setPdmtimesday(int pdmtimesday) {
        this.pdmtimesday = pdmtimesday;
    }

    public int getPdkind() {
        return pdkind;
    }

    public void setPdkind(int pdkind) {
        this.pdkind = pdkind;
    }

    public String getPdtxtc() {
        return pdtxtc;
    }

    public void setPdtxtc(String pdtxtc) {
        this.pdtxtc = pdtxtc;
    }

    public int getPdtxtfsize() {
        return pdtxtfsize;
    }

    public void setPdtxtfsize(int pdtxtfsize) {
        this.pdtxtfsize = pdtxtfsize;
    }

    public int getPdseq() {
        return pdseq;
    }

    public void setPdseq(int pdseq) {
        this.pdseq = pdseq;
    }

    public int getPdkeepl() {
        return pdkeepl;
    }

    public void setPdkeepl(int pdkeepl) {
        this.pdkeepl = pdkeepl;
    }

    public String getPdfromt() {
        return pdfromt;
    }

    public void setPdfromt(String pdfromt) {
        this.pdfromt = pdfromt;
    }

    public String getPdtot() {
        return pdtot;
    }

    public void setPdtot(String pdtot) {
        this.pdtot = pdtot;
    }

    public int getPdinvalid() {
        return pdinvalid;
    }

    public void setPdinvalid(int pdinvalid) {
        this.pdinvalid = pdinvalid;
    }

    public String getLatmodi() {
        return latmodi;
    }

    public void setLatmodi(String latmodi) {
        this.latmodi = latmodi;
    }

    public int getCiid() {
        return ciid;
    }

    public void setCiid(int ciid) {
        this.ciid = ciid;
    }

    public String getCiname() {
        return ciname;
    }

    public void setCiname(String ciname) {
        this.ciname = ciname;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
