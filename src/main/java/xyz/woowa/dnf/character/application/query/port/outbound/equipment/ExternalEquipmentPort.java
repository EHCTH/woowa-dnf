package xyz.woowa.dnf.character.application.query.port.outbound.equipment;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.With;

import java.util.Collections;
import java.util.List;

public interface ExternalEquipmentPort {

    EquipmentPayload fetchPayload(String serverId, String id);

    ItemDetail itemDetail(String itemId);

    UpgradeInfoDetail upgradeInfoDetail(String itemId);


    @With
    record EquipmentPayload(
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Item> equipment,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<SetItem> setItemInfo
    ) {
    }


    @With
    record Item(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String slotId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String slotName,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemType,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeDetailId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeDetail,
            Rarity itemRarity,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String setItemId,         // null 허용 (Optional 안 써도 Jackson이 null 주입)
            @JsonSetter(nulls = Nulls.AS_EMPTY) String setItemName,       // null 허용
            @JsonSetter(nulls = Nulls.AS_EMPTY) String reinforce,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String amplificationName, // null 허용
            Enchant enchant,    // null 허용
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Stat> status,
            // 혹시 루트에 status가 안 온다면 제거해도 됨
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Tune> tune,
            FusionOption fusionOption,
            UpgradeInfo upgradeInfo,
            ItemDetail itemDetail,
            UpgradeInfoDetail upgradeInfoDetail
    ) {
    }

    enum Rarity {
        태초, 에픽, 레전더리, 유니크, 레어, 언커먼, 커먼, 크로니클, 신화, 언노운
    }


    record Enchant(@JsonSetter(nulls = Nulls.AS_EMPTY) List<Stat> status,
                   @JsonSetter(nulls = Nulls.AS_EMPTY) List<ReinforceSkill> reinforceSkill) {
        public List<Stat> getStatus() {
            return status;
        }

        public List<Stat> getSkills() {
            if (reinforceSkill.isEmpty()) {
                return Collections.emptyList();
            }
            return reinforceSkill.getFirst().skills();
        }
    }

    record ReinforceSkill(
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Stat> skills) {
    }

    record Stat(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String name,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String value) {
    }

    record Tune(
            @JsonSetter(nulls = Nulls.AS_EMPTY) Integer level,
            @JsonSetter(nulls = Nulls.AS_EMPTY) Integer setPoint,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Stat> status,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String name
    ) {

    }

    record FusionOption(@JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Option> options) {
        public record Option(
                @JsonSetter(nulls = Nulls.AS_EMPTY) String buff,
                @JsonSetter(nulls = Nulls.AS_EMPTY) String explain,
                @JsonSetter(nulls = Nulls.AS_EMPTY) String explainDetail,
                @JsonSetter(nulls = Nulls.AS_EMPTY) String buffExplain,
                @JsonSetter(nulls = Nulls.AS_EMPTY) String buffExplainDetail
        ) {
        }
    }


    record UpgradeInfo(@JsonSetter(nulls = Nulls.AS_EMPTY) String itemId,
                       @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName,
                       @JsonSetter(nulls = Nulls.AS_EMPTY) String itemRarity) {
    }


    record SetItem(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String setItemId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String setItemName,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String setItemRarityName,
            Active active
    ) {
    }


    record Active(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String explain,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String buffExplain,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Stat> status,
            SetPointRange setPoint,
            @JsonSetter(nulls = Nulls.AS_EMPTY) Integer setEquipCount
    ) {
    }


    record SetPointRange(@JsonSetter(nulls = Nulls.AS_EMPTY) Integer current,
                         @JsonSetter(nulls = Nulls.AS_EMPTY) Integer min,
                         @JsonSetter(nulls = Nulls.AS_EMPTY) Integer max) {
    }


    record ItemDetail(@JsonSetter(nulls = Nulls.AS_EMPTY) String itemExplain,
                      @JsonSetter(nulls = Nulls.AS_EMPTY) String itemExplainDetail,
                      @JsonSetter(nulls = Nulls.AS_EMPTY) String fame,
                      @JsonSetter(nulls = Nulls.AS_EMPTY) String itemFlavorText,
                      @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Stat> itemStatus,
                      @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Tune> tune,
                      ItemBuff itemBuff) {
        public static ItemDetail EMPTY = new ItemDetail("", "", "", "", Collections.emptyList(), Collections.emptyList(), ItemBuff.EMPTY);

        public String getItemBuff() {
            if (itemBuff == null) {
                return "";
            }
            return itemBuff.explain;
        }
    }

    record UpgradeInfoDetail(@JsonSetter(nulls = Nulls.AS_EMPTY) String fame,
                             Rarity itemRarity,
                             @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeDetailId,
                             @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeDetail,
                             @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Stat> itemStatus,
                             @JsonSetter(nulls = Nulls.AS_EMPTY) String itemFlavorText) {
        public static UpgradeInfoDetail EMPTY = new UpgradeInfoDetail("", Rarity.언노운,"","", Collections.emptyList(), "");
    }


    record ItemBuff(@JsonSetter(nulls = Nulls.AS_EMPTY) String explain) {
        public static ItemBuff EMPTY = new ItemBuff("");
    }
}

