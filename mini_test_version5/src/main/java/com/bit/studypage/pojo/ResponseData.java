package com.bit.studypage.pojo;

import java.util.List;

public class ResponseData {
    public Response response;

    public static class Response {
        public Header header;
        public Body body;

        public static class Header {
            public String resultCode;
            public String resultMsg;
        }

        public static class Body {
            public Items items;

            public static class Items {
                public List<Item> item;

                public static class Item {
                    public String docexamenddt;
                    public String docexamstartdt;
                    public String docpassdt;
                    public String docregenddt;
                    public String docregstartdt;
                    public String docsubmitenddt;
                    public String docsubmitstartdt;
                    public String implplannm;
                    public String jmfldnm;
                    public String mdobligfldcd;
                    public String mdobligfldnm;
                    public String obligfldcd;
                    public String obligfldnm;
                    public String pracexamenddt;
                    public String pracexamstartdt;
                    public String pracpassenddt;
                    public String pracpassstartdt;
                    public String pracregenddt;
                    public String pracregstartdt;
                }
            }
        }
    }
}

