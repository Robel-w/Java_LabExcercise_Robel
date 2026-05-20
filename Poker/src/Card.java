public record Card(String rank, String suit) {

    public boolean isRed() {
        return suit.equals("♥") || suit.equals("♦");
    }

    public int rankIndex() {
        String[] ranks = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank)) return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return rank + suit;
    }
}