public class HandResult implements Comparable<HandResult> {
    public final int tier;
    public final int[] tieBreak;
    public final String name;

    public HandResult(int tier, int[] tieBreak, String name) {
        this.tier = tier;
        this.tieBreak = tieBreak;
        this.name = name;
    }

    @Override
    public int compareTo(HandResult other) {
        if (this.tier != other.tier) return Integer.compare(this.tier, other.tier);

        for (int i = 0; i < Math.min(this.tieBreak.length, other.tieBreak.length); i++) {
            if (this.tieBreak[i] != other.tieBreak[i]) {
                return Integer.compare(this.tieBreak[i], other.tieBreak[i]);
            }
        }
        return 0;
    }
}