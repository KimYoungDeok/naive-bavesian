package count;

/**
 * Description.
 *
 * @author Youngdeok Kim
 * @since 1.0
 */
public class Count {
    private String name;
    private int count;

    public Count(String name) {
        this.name = name;
    }
	public Count() {
	}
    public void increment(){
        count++;
    }
	public void increment(int weight) {
		count += weight;
	}

    public String getName() {
        return name;
    }

    public int getCount(){
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Count count = (Count) o;

        if (name != null ? !name.equals(count.name) : count.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }


}
