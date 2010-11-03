package main.ui;

import java.io.*;
import javax.swing.table.AbstractTableModel;

import main.utils.FileUtils;

public class DirectoryModel extends AbstractTableModel /*implements TableModelListener*/ {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected File directory;
    protected String[] children;
    protected int rowCount;

    public DirectoryModel() {
        init();
    }

    public DirectoryModel( File dir ) {
        init();
        directory = dir;
        children = dir.list();
        rowCount = children.length;
    }

    protected void init() {
    }

    public void setDirectory( File dir ) {
        if ( dir != null ) {
            directory = dir;
            children = dir.list();
            rowCount = children.length;
        }
        else {
            directory = null;
            children = null;
            rowCount = 0;
        }
        fireTableDataChanged();
    }

    public int getRowCount() {
        return children != null ? rowCount : 0;
    }

    public int getColumnCount() {
        return children != null ? 3 :0;
    }

    public String getColumnName( int column ) {
        switch ( column ) {
        case 0:
            return "Type";
        case 1:
            return "Name";
        case 2:
            return "Size";
        default:
            return "unknown";
        }
    }

    public Object getValueAt(int row, int column) {
    	if ( directory == null || children == null ) {
    		return null;
    	}

    	File fileSysEntity = new File( directory, children[row] );

    	switch ( column ) {
    	case 0:
    		return fileSysEntity.isDirectory() ? "Folder" : "File";
    	case 1:
    		return  fileSysEntity.getName();
    	case 2:
//    		if ( fileSysEntity.isDirectory() ) {
//    			return "--";
//    		}
//    		else {
    			return FileUtils.getFormattedSize(fileSysEntity);
//    		}
    	default:
    		return "";
    	}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass( int column ) {
        if ( column == 0 ) {
            return getValueAt( 0, column).getClass();
        }
        else {
            return super.getColumnClass( column );
        }
    }
}                   

