package theRanger.util;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRanger.powers.brown.CamouflagePower;

public class StealthHelper {

    public static boolean StealthCheck(AbstractCreature owner, AbstractMonster target){
        if(owner.hasPower(CamouflagePower.POWER_ID)){
            int aux1 = owner.getPower(CamouflagePower.POWER_ID).amount;
            int aux2 = 0;
            if(aux1 <= 0) return false;
            else{
                if(target == null) return true;
                if (target.intent == AbstractMonster.Intent.ATTACK || target.intent == AbstractMonster.Intent.ATTACK_BUFF || target.intent == AbstractMonster.Intent.ATTACK_DEBUFF || target.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
                    aux2 = target.getIntentDmg();
                }
                if(aux1 > aux2) return true;
            }
        }
        return false;
    }
}
