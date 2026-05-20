import java.util.*;

public class HandEvaluator {

    public static HandResult evaluate(Card[] hand) {
        int[] ranks = new int[5];
        for (int i = 0; i < 5; i++) ranks[i] = hand[i].rankIndex();
        Arrays.sort(ranks);

        // Frequency
        Map<Integer, Integer> freq = new HashMap<>();
        for (int r : ranks) freq.merge(r, 1, Integer::sum);

        List<Map.Entry<Integer,Integer>> entries = new ArrayList<>(freq.entrySet());
        entries.sort((a, b) -> b.getValue() != a.getValue() ?
                b.getValue() - a.getValue() : b.getKey() - a.getKey());

        boolean flush = Arrays.stream(hand).map(Card::suit).distinct().count() == 1;
        boolean straight = ranks[4] - ranks[0] == 4 && freq.size() == 5;
        boolean wheel = ranks[0] == 0 && ranks[1] == 1 && ranks[2] == 2 && ranks[3] == 3 && ranks[4] == 12;

        int[] sortedDesc = new int[5];
        for (int i = 0; i < 5; i++) sortedDesc[i] = ranks[4 - i];

        int[] counts = entries.stream().mapToInt(Map.Entry::getValue).toArray();
        int[] tieByFreq = entries.stream().mapToInt(Map.Entry::getKey).toArray();

        // Straight & Royal Flush
        if ((straight || wheel) && flush) {
            if (straight && ranks[4] == 12) {
                return new HandResult(9, sortedDesc, "Royal Flush 👑");
            }
            int[] tb = wheel ? new int[]{5,4,3,2,1} : sortedDesc;
            return new HandResult(8, tb, "Straight Flush");
        }

        if (counts[0] == 4) return new HandResult(7, tieByFreq, "Four of a Kind");
        if (counts[0] == 3 && counts[1] == 2) return new HandResult(6, tieByFreq, "Full House");
        if (flush) return new HandResult(5, sortedDesc, "Flush");
        if (straight || wheel) {
            int[] tb = wheel ? new int[]{5,4,3,2,1} : sortedDesc;
            return new HandResult(4, tb, "Straight");
        }
        if (counts[0] == 3) return new HandResult(3, tieByFreq, "Three of a Kind");
        if (counts[0] == 2 && counts[1] == 2) return new HandResult(2, tieByFreq, "Two Pair");
        if (counts[0] == 2) return new HandResult(1, tieByFreq, "One Pair");

        return new HandResult(0, sortedDesc, "High Card");
    }
}