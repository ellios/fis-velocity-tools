package com.baidu.fis.jsp;

import com.baidu.fis.util.Resource;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 2betop on 15/1/15.
 */
public class HeadTag extends BodyTagSupport implements DynamicAttributes {

    private Map<String, Object> attrs = new HashMap<String, Object>();

    @Override
    public void setDynamicAttribute(String s, String s2, Object o) throws JspException {
        attrs.put(s2, o);
    }

    /**
     * Default processing of the start tag returning EVAL_BODY_BUFFERED.
     *
     * @return EVAL_BODY_BUFFERED
     * @throws javax.servlet.jsp.JspException if an error occurred while processing this tag
     */
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            out.append("<head");

            for (String key:attrs.keySet()) {
                Object value = attrs.get(key);

                out.append(" ");
                out.append(key);
                out.append("=\"");
                out.append(value.toString().replace("\"", "&quote;"));
                out.append("\"");
            }

            out.append(">");
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        return EVAL_BODY_BUFFERED;
    }

    /**
     * Default processing of the end tag returning EVAL_PAGE.
     *
     * @return EVAL_PAGE
     * @throws javax.servlet.jsp.JspException if an error occurred while processing this tag
     */
    @Override
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            out.write(getBodyContent().getString());
            out.write(Resource.STYLE_PLACEHOLDER);
            out.write("</head>");
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        return EVAL_PAGE;
    }

    /**
     * Release state.
     */
    @Override
    public void release() {
        attrs.clear();
        super.release();
    }
}