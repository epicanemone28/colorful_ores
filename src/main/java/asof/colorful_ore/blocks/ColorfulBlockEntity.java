package asof.colorful_ore.blocks;

import asof.colorful_ore.ColorfulOres;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static asof.colorful_ore.ColorfulOres.COLORFUL_BLOCK_ENTITY;

public class ColorfulBlockEntity extends BlockEntity {
    public int rgb = ColorfulOres.defaultColor.getRGB();

    public ColorfulBlockEntity(BlockPos pos, BlockState state) {
        super(COLORFUL_BLOCK_ENTITY.get(),pos,state);
    }

    public int getTint(){
        return this.rgb;
    }

    public void setTint(int rgb){
        this.rgb = rgb;
    }

    // Read values from the passed CompoundTag here.
    @Override
    @ParametersAreNonnullByDefault
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        // Will default to 0 if absent. See the NBT article for more information.
        this.rgb = tag.getInt("rgb");
    }

    // Save values into the passed CompoundTag here.
    @Override
    @ParametersAreNonnullByDefault
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("rgb", this.rgb);
    }

    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.
    @Override
    @ParametersAreNonnullByDefault
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // Optionally: Run some custom logic when the packet is received.
    // The super/default implementation forwards to #loadAdditional.
    @Override
    @ParametersAreNonnullByDefault
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
        // Do whatever you need to do here.
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (level == null) return;

        BlockState state = getBlockState();
        level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
    }
}
