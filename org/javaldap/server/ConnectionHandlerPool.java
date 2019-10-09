package org.javaldap.server;

/**
 * Insert the type's description here.
 * Creation date: (6/10/2000 12:16:26 PM)
 * @author: Administrator
 */
class ConnectionHandlerPool extends org.javaldap.server.util.ObjectPool {
    private static ConnectionHandlerPool chp = null;

    /**
     * ConnectionHandlerPool constructor comment.
     */
    private ConnectionHandlerPool() {
        super();
    }
    /**
     * create method comment.
     */
    public Object create() throws Exception {
        return new ConnectionHandler();
    }
    /**
     * expire method comment.
     */
    public void expire(Object o) {
    }

    public static ConnectionHandlerPool getInstance() {
        if (chp == null) {
            chp = new ConnectionHandlerPool();
        }
        return chp;
    }
    /**
     * validate method comment.
     */
    public boolean validate(Object o) {
        return false;
    }
}
