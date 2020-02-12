package com.chatbot.admin.utils;

public class Constant {

	public static final String BUTTON_NOTFOUND_MSG= "No button found by this id ";
	
	
	
	public enum MessageTypes {
	    TEXT_MSG("TEXTMESSAGE"),
	    QUICKREPLY_MSG("QUICKREPLYMESSAGE"),
	    GENERIC_MSG("GENERICTEMPLATEMESSAGE"),
	    BUTTON_MSG("BUTTONTEMPLATE");
	    

	    private final String type;

	    /**
	     * @param text
	     */
	    MessageTypes(final String type) {
	        this.type = type;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return type;
	    }
	}
	
}
