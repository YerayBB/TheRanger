package theRanger.util;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theRanger.interfaces.OnEmptinessTrigger;
import theRanger.interfaces.OnOverchargeTrigger;
import theRanger.interfaces.OnPreShotTrigger;
import theRanger.interfaces.OnShotTrigger;
import theRanger.powers.brown.EssencePower;

import java.util.Iterator;

public class ShotHelper {
    //returns de amount you shot, also actually shots the corresponding amount
    //triggering the events preshot, and onshot
    public static int Shot(int amount){
        int res = 0;
        AbstractPlayer p = AbstractDungeon.player;

        int cost = amount;
        cost = PreShotCalls(cost);


        if(p.hasPower(EssencePower.POWER_ID)){
            AbstractPower p1 = p.getPower(EssencePower.POWER_ID);
            int a  = p1.amount;
            if(a != 0){
                if(a >= cost){
                    p1.reducePower(cost);
                    ShotCalls();
                    return amount;
                }
                else{
                    return a;
                }
            }
        }
        return 0;
    }



    private static void ShotCalls(){
        //RelicCall
        for(AbstractRelic r1: AbstractDungeon.player.relics){
            if(r1 instanceof OnShotTrigger){
                ((OnShotTrigger) r1).OnShot();
            }
        }
        //RelicCall
        //HandCall
        for(AbstractCard c1: AbstractDungeon.player.hand.group){
            if(c1 instanceof OnShotTrigger){
                ((OnShotTrigger) c1).OnShot();
            }
        }
        //HandCall

        //DrawpileCall
        for(AbstractCard c2: AbstractDungeon.player.drawPile.group){
            if(c2 instanceof OnShotTrigger){
                ((OnShotTrigger) c2).OnShot();
            }
        }
        //DrawpileCall

        //DiscardpileCall
        for(AbstractCard c3: AbstractDungeon.player.discardPile.group){
            if(c3 instanceof OnShotTrigger){
                ((OnShotTrigger) c3).OnShot();
            }
        }
        //DiscardpileCall

        //ExhaustpileCall
        for(AbstractCard c4: AbstractDungeon.player.exhaustPile.group){
            if(c4 instanceof OnShotTrigger){
                ((OnShotTrigger) c4).OnShot();
            }
        }
        //ExhaustpileCall

        //PowersCall
        for(AbstractPower p1: AbstractDungeon.player.powers){
            if(p1 instanceof OnShotTrigger){
                ((OnShotTrigger) p1).OnShot();
            }
        }

        //MonstersCall
        Iterator var5 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var5.hasNext()) {
            AbstractMonster m3 = (AbstractMonster) var5.next();
            if (!m3.isDeadOrEscaped() && !m3.halfDead) {
                for (AbstractPower p2 : m3.powers) {
                    if(p2 instanceof OnShotTrigger){
                        ((OnShotTrigger) p2).OnShot();
                    }
                }
            }
        }
        //MonstersCall
    }

    //PreShot returns the amount of essence that will be consumed to make the shot
    private static int PreShotCalls(int baseCost){
        //RelicCall
        int result = baseCost;
        for(AbstractRelic r1: AbstractDungeon.player.relics){
            if(r1 instanceof OnPreShotTrigger){
                result = ((OnPreShotTrigger) r1).OnPreShot(result);
            }
        }
        //RelicCall

        /*There are no cards that use this trigger
        //HandCall
        for(AbstractCard c1: AbstractDungeon.player.hand.group){
            if(c1 instanceof OnShotTrigger){
                ((OnShotTrigger) c1).OnShot();
            }
        }
        //HandCall

        //DrawpileCall
        for(AbstractCard c2: AbstractDungeon.player.drawPile.group){
            if(c2 instanceof OnShotTrigger){
                ((OnShotTrigger) c2).OnShot();
            }
        }
        //DrawpileCall

        //DiscardpileCall
        for(AbstractCard c3: AbstractDungeon.player.discardPile.group){
            if(c3 instanceof OnShotTrigger){
                ((OnShotTrigger) c3).OnShot();
            }
        }
        //DiscardpileCall

        //ExhaustpileCall
        for(AbstractCard c4: AbstractDungeon.player.exhaustPile.group){
            if(c4 instanceof OnShotTrigger){
                ((OnShotTrigger) c4).OnShot();
            }
        }
        //ExhaustpileCall
         */

        //PowersCall
        for(AbstractPower p1: AbstractDungeon.player.powers){
            if(p1 instanceof OnPreShotTrigger){
                result = ((OnPreShotTrigger) p1).OnPreShot(result);
            }
        }

        //MonstersCall
        Iterator var5 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var5.hasNext()) {
            AbstractMonster m3 = (AbstractMonster) var5.next();
            if (!m3.isDeadOrEscaped() && !m3.halfDead) {
                for (AbstractPower p2 : m3.powers) {
                    if(p2 instanceof OnPreShotTrigger){
                        result = ((OnPreShotTrigger) p2).OnPreShot(result);
                    }
                }
            }
        }
        //MonstersCall

        return result;
    }

    public static void TriggerEmptiness(){
        for(AbstractCard c1: AbstractDungeon.player.hand.group){
            if(c1 instanceof OnEmptinessTrigger){
                ((OnEmptinessTrigger) c1).OnEmptiness();
            }
        }
        for(AbstractCard c2: AbstractDungeon.player.drawPile.group){
            if(c2 instanceof OnEmptinessTrigger){
                ((OnEmptinessTrigger) c2).OnEmptiness();
            }
        }
        for(AbstractCard c3: AbstractDungeon.player.discardPile.group){
            if(c3 instanceof OnEmptinessTrigger){
                ((OnEmptinessTrigger) c3).OnEmptiness();
            }
        }
        for(AbstractCard c4: AbstractDungeon.player.exhaustPile.group){
            if(c4 instanceof OnEmptinessTrigger){
                ((OnEmptinessTrigger) c4).OnEmptiness();
            }
        }

    }

    public static void TriggerOvercharge(){
        for(AbstractCard c1: AbstractDungeon.player.hand.group){
            if(c1 instanceof OnOverchargeTrigger){
                ((OnOverchargeTrigger) c1).OnOvercharge();
            }
        }
        for(AbstractCard c2: AbstractDungeon.player.drawPile.group){
            if(c2 instanceof OnOverchargeTrigger){
                ((OnOverchargeTrigger) c2).OnOvercharge();
            }
        }
        for(AbstractCard c3: AbstractDungeon.player.discardPile.group){
            if(c3 instanceof OnOverchargeTrigger){
                ((OnOverchargeTrigger) c3).OnOvercharge();
            }
        }
        for(AbstractCard c4: AbstractDungeon.player.exhaustPile.group){
            if(c4 instanceof OnOverchargeTrigger){
                ((OnOverchargeTrigger) c4).OnOvercharge();
            }
        }
    }
}
