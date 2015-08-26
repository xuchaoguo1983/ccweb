package com.pengyang.ccweb.bo;

import java.io.Serializable;

public class PageMeta implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static int DEFAULT_PAGE_ROW = 10;

	private int pageRow = DEFAULT_PAGE_ROW;
	private int totalRows = 0;
	private int page = 1;

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageRow() {
		return pageRow;
	}

	public void setPageRow(int pageRow) {
		this.pageRow = pageRow;
	}

	public int getTotalPage() {
		if (totalRows <= 0)
			return 1;

		return 1 + (totalRows - 1) / getPageRow();
	}
}
