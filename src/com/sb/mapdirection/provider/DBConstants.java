/*******************************************************************************
 * Copyright (c) 2011 by Vivox Inc.
 *  Permission to use, copy, modify or distribute this software in binary or source form
 *  for any purpose is allowed only under explicit prior consent in writing from Vivox Inc.
 * THE SOFTWARE IS PROVIDED "AS IS" AND VIVOX DISCLAIMS
 *  ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL VIVOX
 *  BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL
 *  DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR
 *  PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS
 *  ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS
 *  SOFTWARE.
 ******************************************************************************/
package com.sb.mapdirection.provider;

import android.net.Uri;

public class DBConstants {

	public static final String CONTENT_VENUE = "location";
	public static final String COLUMN_POSTAL_CODE = "postal_code";
	public static String AUTHORITY = "com.mapvenue.provider";
	
	public static final Uri URI_VENUE = Uri.parse("content://"+AUTHORITY+"/"+CONTENT_VENUE);
	public static final String COLUMN_LAT = "lat";
	public static final String COLUMN_LONG = "lng";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_TYPE = "type";

}
