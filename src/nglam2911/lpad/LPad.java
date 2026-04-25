package nglam2911.lpad;

import arc.util.CommandHandler;
import mindustry.Vars;
import mindustry.mod.Mod;

public class LPad extends Mod{

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.register("lpad_redirect", "Redirect all export on planet to current playing sector", args -> {
            var state = Vars.state;
            if (!check()) return;
            var currentSector = state.getSector();
            var planet = state.getPlanet();
            for (var sector : planet.sectors){
                if (!sector.isCaptured()) continue;
                var secInfo = sector.info;
                secInfo.destination = currentSector;
            }
            currentSector.info.refreshImportRates(planet);
            Vars.player.sendMessage("[green]All exports have been redirected to sector " + currentSector.name() + "!");
        });
    }

    private boolean check(){
        var state = Vars.state;
        if (!state.isCampaign()) {
            Vars.player.sendMessage("[scarlet]This command can only be used in campaign mode.");
            return false;
        }
        if (Vars.net.client()) {
            Vars.player.sendMessage("[scarlet]This command can only be used on host");
            return false;
        }
        return true;
    }
}