package natc.types;

public interface Number {

	public Number getValue();
	public void   setValue( Number value );
	public void   add( Number value );
	public void   subtract( Number value );
	public void   subtractFrom( Number value );
	public void   multiply( Number value );
	public void   divide( Number value );
	public void   divideBy( Number value );
}
