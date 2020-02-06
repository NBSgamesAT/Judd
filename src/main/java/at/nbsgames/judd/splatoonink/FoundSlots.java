package at.nbsgames.judd.splatoonink;

public class FoundSlots{

    private BattleSlotV2 turf1;
    private BattleSlotV2 turf2;
    private BattleSlotV2 ranked1;
    private BattleSlotV2 ranked2;
    private BattleSlotV2 league1;
    private BattleSlotV2 league2;

    FoundSlots(BattleSlotV2 turf1, BattleSlotV2 turf2, BattleSlotV2 ranked1, BattleSlotV2 ranked2, BattleSlotV2 league1, BattleSlotV2 league2){
        this.turf1 = turf1;
        this.turf2 = turf2;
        this.ranked1 = ranked1;
        this.ranked2 = ranked2;
        this.league1 = league1;
        this.league2 = league2;
    }

    public BattleSlotV2 getTurf1() {
        return turf1;
    }

    public BattleSlotV2 getTurf2() {
        return turf2;
    }

    public BattleSlotV2 getRanked1() {
        return ranked1;
    }

    public BattleSlotV2 getRanked2() {
        return ranked2;
    }

    public BattleSlotV2 getLeague1() {
        return league1;
    }

    public BattleSlotV2 getLeague2() {
        return league2;
    }
}
