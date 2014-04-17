package frameWork.core.viewCompiler.script.scriptVar;

import static frameWork.core.viewCompiler.script.scriptVar.Util.*;

public class CScriptVar {
	
	public static final int SCRIPTVAR_UNDEFINED = 0;
	public static final int SCRIPTVAR_FUNCTION = 1;
	public static final int SCRIPTVAR_OBJECT = 2;
	public static final int SCRIPTVAR_ARRAY = 4;
	public static final int SCRIPTVAR_DOUBLE = 8;
	public static final int SCRIPTVAR_INTEGER = 16;
	public static final int SCRIPTVAR_STRING = 32;
	public static final int SCRIPTVAR_NULL = 64;
	public static final int SCRIPTVAR_NUMERICMASK = SCRIPTVAR_NULL | SCRIPTVAR_DOUBLE | SCRIPTVAR_INTEGER;
	public static final int SCRIPTVAR_VARTYPEMASK = SCRIPTVAR_STRING | SCRIPTVAR_FUNCTION | SCRIPTVAR_OBJECT
	        | SCRIPTVAR_ARRAY | SCRIPTVAR_NUMERICMASK;
	
	public String data;
	public CScriptVarLink firstChild;
	CScriptVarLink lastChild;
	int refs;
	int flags;
	int intData;
	double doubleData;
	
	public CScriptVar() {
		refs = 0;
		firstChild = null;
		lastChild = null;
		flags = 0;
		data = TINYJS_BLANK_DATA;
		intData = 0;
		doubleData = 0;
	}
	
	public CScriptVarLink addChild(final String childName, CScriptVar child) {
		if (isUndefined()) {
			flags = SCRIPTVAR_OBJECT;
		}
		// if no child supplied, create one
		if (child == null) {
			child = new ScriptUndefined();
		}
		
		final CScriptVarLink link = new CScriptVarLink(child, childName);
		link.owned = true;
		if (lastChild != null) {
			lastChild.nextSibling = link;
			link.prevSibling = lastChild;
			lastChild = link;
		}
		else {
			firstChild = link;
			lastChild = link;
		}
		return link;
	}
	
	public CScriptVarLink addChildNoDup(final String childName, final CScriptVar child) throws Exception {
		// if no child supplied, create one
		CScriptVar scriptVar = child;
		if (scriptVar == null) {
			scriptVar = new ScriptUndefined();
		}
		
		CScriptVarLink v = findChild(childName);
		if (v != null) {
			v.replaceWith(scriptVar);
		}
		else {
			v = addChild(childName, scriptVar);
		}
		
		return v;
	}
	
	public CScriptVar mathsOp(final CScriptVar b, final String op) throws Exception {
		// do maths...
		if ((isUndefined()) && (b.isUndefined())) {
			if (op.equals(LEX_NEQUAL)) {
				return new ScriptInteger(0);
			}
			else if (op.equals(LEX_EQUALEQUAL)) {
				return new ScriptInteger(1);
			}
			return new ScriptUndefined();
			
		}
		else if (((isNumeric()) || (isUndefined())) && ((b.isNumeric()) || (b.isUndefined()))) {
			if ((!isDouble()) && (!b.isDouble())) {
				// use ints
				final int da = getInt();
				final int db = b.getInt();
				return mathsOp(da, db, op);
			}
			// use doubles
			final double da = getDouble();
			final double db = b.getDouble();
			return mathsOp(da, db, op);
		}
		else if (isArray() || isObject()) {
			return mathsOp(this, b, op);
		}
		else {
			final String da = getString();
			final String db = b.getString();
			return mathsOp(da, db, op);
		}
	}
	
	public CScriptVar mathsOp(final int da, final int db, final String op) throws Exception {
		switch ( op ) {
			case LEX_AND :
				return new ScriptInteger(da & db);
			case LEX_OR :
				return new ScriptInteger(da | db);
			case LEX_XOR :
				return new ScriptInteger(da ^ db);
			case LEX_PERCENT :
				return new ScriptInteger(da % db);
			case LEX_LEQUAL :
				return new ScriptInteger(da <= db ? 1 : 0);
			case LEX_GEQUAL :
				return new ScriptInteger(da >= db ? 1 : 0);
			case LEX_ANGLE_BRACKETS_LEFT :
				return new ScriptInteger(da < db ? 1 : 0);
			case LEX_ANGLE_BRACKETS_RIGHT :
				return new ScriptInteger(da > db ? 1 : 0);
			case LEX_ASTERISK :
				return new ScriptInteger(da * db);
			case LEX_SLASH :
				return new ScriptInteger(da / db);
			case LEX_PLUS :
				return new ScriptInteger(da + db);
			case LEX_MINUS :
				return new ScriptInteger(da - db);
			default :
				throw new Exception("Operation " + op + " not supported on the Int datatype");
		}
	}
	
	public CScriptVar mathsOp(final double da, final double db, final String op) throws Exception {
		switch ( op ) {
			case LEX_LEQUAL :
				return new ScriptInteger(da <= db ? 1 : 0);
			case LEX_GEQUAL :
				return new ScriptInteger(da >= db ? 1 : 0);
			case LEX_ANGLE_BRACKETS_LEFT :
				return new ScriptInteger(da < db ? 1 : 0);
			case LEX_ANGLE_BRACKETS_RIGHT :
				return new ScriptInteger(da > db ? 1 : 0);
			case LEX_ASTERISK :
				return new ScriptDouble(da * db);
			case LEX_SLASH :
				return new ScriptDouble(da / db);
			case LEX_PLUS :
				return new ScriptDouble(da + db);
			case LEX_MINUS :
				return new ScriptDouble(da - db);
			default :
				throw new Exception("Operation " + op + " not supported on the Double datatype");
		}
	}
	
	public CScriptVar mathsOp(final String da, final String db, final String op) throws Exception {
		switch ( op ) {
			case LEX_PLUS :
				return new ScriptString(da + db);
			default :
				throw new Exception("Operation " + op + " not supported on the string datatype");
		}
	}
	
	public CScriptVar mathsOp(final CScriptVar da, final CScriptVar db, final String op) throws Exception {
		throw new Exception("Operation " + op + " not supported on the Array datatype");
	}
	
	public CScriptVarLink findChild(final String childName) {
		CScriptVarLink v = firstChild;
		while (v != null) {
			if (v.name.equals(childName)) {
				return v;
			}
			v = v.nextSibling;
		}
		return null;
	}
	
	private boolean isInt() {
		return (flags & SCRIPTVAR_INTEGER) != 0;
	}
	
	private boolean isDouble() {
		return (flags & SCRIPTVAR_DOUBLE) != 0;
	}
	
	public boolean isString() {
		return (flags & SCRIPTVAR_STRING) != 0;
	}
	
	private boolean isNumeric() {
		return (flags & SCRIPTVAR_NUMERICMASK) != 0;
	}
	
	public boolean isFunction() {
		return (flags & SCRIPTVAR_FUNCTION) != 0;
	}
	
	private boolean isObject() {
		return (flags & SCRIPTVAR_OBJECT) != 0;
	}
	
	public boolean isArray() {
		return (flags & SCRIPTVAR_ARRAY) != 0;
	}
	
	private boolean isUndefined() {
		return (flags & SCRIPTVAR_VARTYPEMASK) == SCRIPTVAR_UNDEFINED;
	}
	
	private boolean isNull() {
		return (flags & SCRIPTVAR_NULL) != 0;
	}
	
	public boolean isBasic() {
		return firstChild == null;
	}
	
	public boolean getBool() {
		return getInt() != 0;
	}
	
	public int getInt() {
		/* strtol understands about hex and octal */
		if (isInt()) {
			return intData;
		}
		if (isNull()) {
			return 0;
		}
		if (isUndefined()) {
			return 0;
		}
		if (isDouble()) {
			return (int) doubleData;
		}
		return 0;
	}
	
	public void setInt(final int val) {
		flags = (flags & ~SCRIPTVAR_VARTYPEMASK) | SCRIPTVAR_INTEGER;
		intData = val;
		doubleData = 0;
		data = TINYJS_BLANK_DATA;
	}
	
	public int getArrayLength() {
		int highest = -1;
		if (!isArray()) {
			return 0;
		}
		
		CScriptVarLink link = firstChild;
		while (link != null) {
			if (isNumber(link.name)) {
				try {
					final int value = Integer.parseInt(link.name);
					if (value > highest) {
						highest = value;
					}
				}
				catch (final NumberFormatException e) {
					int value = 0;
					try {
						for (int i = 1; i < link.name.length(); i++) {
							value = Integer.parseInt(link.name.substring(0, i));
						}
					}
					catch (final NumberFormatException e2) {
						//NOP
					}
					if (value > highest) {
						highest = value;
					}
				}
			}
			link = link.nextSibling;
		}
		return highest + 1;
	}
	
	private boolean isNumber(final String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!isNumeric(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isNumeric(final char ch) {
		return (ch >= '0') && (ch <= '9');
	}
	
	public String getString() {
		/*
		 *  Because we can't return a string that is generated on demand.
		 * I should really just use char* :)
		 */
		final String s_null = LEX_R_NULL;
		final String s_undefined = "undefined";
		if (isInt()) {
			data = String.valueOf(intData);
			return data;
		}
		if (isDouble()) {
			data = String.valueOf(doubleData);
			return data;
		}
		if (isNull()) {
			return s_null;
		}
		if (isUndefined()) {
			return s_undefined;
		}
		// are we just a string here?
		return data;
	}
	
	public CScriptVarLink findChildOrCreate(final String childName) {
		final CScriptVarLink l = findChild(childName);
		if (l != null) {
			return l;
		}
		return addChild(childName, new ScriptUndefined());
	}
	
	public CScriptVar deepCopy() {
		final ScriptUndefined newVar = new ScriptUndefined();
		newVar.copySimpleData(this);
		// copy children
		CScriptVarLink child = firstChild;
		while (child != null) {
			CScriptVar copied;
			// don't copy the 'parent' object...
			if (!child.name.equals(TINYJS_PROTOTYPE_CLASS)) {
				copied = child.var.deepCopy();
			}
			else {
				copied = child.var;
			}
			
			newVar.addChild(child.name, copied);
			child = child.nextSibling;
		}
		return newVar;
	}
	
	void removeAllChildren() throws Exception {
		CScriptVarLink c = firstChild;
		while (c != null) {
			final CScriptVarLink t = c.nextSibling;
			c.close();
			c = t;
		}
		firstChild = null;
		lastChild = null;
	}
	
	private double getDouble() {
		if (isDouble()) {
			return doubleData;
		}
		if (isInt()) {
			return intData;
		}
		if (isNull()) {
			return 0;
		}
		if (isUndefined()) {
			return 0;
		}
		return 0; /* or NaN? */
	}
	
	void copySimpleData(final CScriptVar val) {
		data = val.data;
		intData = val.intData;
		doubleData = val.doubleData;
		flags = (flags & ~SCRIPTVAR_VARTYPEMASK) | (val.flags & SCRIPTVAR_VARTYPEMASK);
	}
	
	public CScriptVar ref() {
		refs++;
		return this;
	}
	
	public void unref() throws Exception {
		if (refs <= 0) {
			throw new Exception("OMFG, we have unreffed too far!");
		}
		if ((--refs) == 0) {
			removeAllChildren();
		}
	}
}