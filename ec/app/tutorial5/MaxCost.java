package ec.app.tutorial5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ec.app.tutorial4.Task;
import ec.app.tutorial4.VirtualMachine;

public class MaxCost implements IAlgorithm {

	public MaxCost() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Object> taskMapping(ArrayList<Task> parentTasks, ArrayList<VirtualMachine> vms, Task t, int j) {
		ArrayList<Object> updatedVals = new ArrayList<Object>();

		t.setAllocation_time(Utility.getMaxFinishTime(parentTasks));

		for (VirtualMachine vm : vms) {
			t.setExe_time((double) t.getTask_size() / vm.getVelocity());

			// double preFinishTime = Utility.getMaxFinishTime(vm.getPriority_queue());
			//
			// t.setStart_time(Utility.getMaxStartTime(preFinishTime,
			// t.getAllocation_time()));
			// t.setWaiting_time();
			// t.setRelative_finish_time();

			double cost = (double) t.getExe_time() * vm.getUnit_cost_vm();
			vm.setCost(cost);

		}

		VirtualMachine vmSel = getVMWithMaxCost(vms);

		t.setExe_time(t.getTask_size() / (double) vmSel.getVelocity());

		double preFinishTime = Utility.getMaxFinishTime(vmSel.getPriority_queue());

		t.setStart_time(Utility.getMaxStartTime(preFinishTime, t.getAllocation_time()));
		t.setWaiting_time();
		t.setRelative_finish_time();
		t.setFinish_time();

		vmSel.setPriority_queue(t);

		updatedVals.add(t);
		updatedVals.add(vmSel);

		return updatedVals;
	}

	private static VirtualMachine getVMWithMaxCost(ArrayList<VirtualMachine> vms) {
		Collections.sort(vms, new Comparator<VirtualMachine>() {
			@Override
			public int compare(VirtualMachine v1, VirtualMachine v2) {
				return Double.compare(v1.getCost(), v2.getCost());
			}
		});

		return vms.get(vms.size() - 1);

	}
	// ArrayList<Object> updatedVals = new ArrayList<Object>();
	//
	// t.setAllocation_time(Utility.getMaxFinishTime(parentTasks));
	// VirtualMachine vmSel = getVMWithMaxUnitCost(vms);
	//
	// t.setExe_time(t.getTask_size() / (double) vmSel.getVelocity());
	//
	// double preFinishTime = Utility.getMaxFinishTime(vmSel.getPriority_queue());
	//
	// t.setStart_time(Utility.getMaxStartTime(preFinishTime,
	// t.getAllocation_time()));
	// t.setWaiting_time();
	// t.setRelative_finish_time();
	// t.setFinish_time();
	//
	// vmSel.setPriority_queue(t);
	//
	// updatedVals.add(t);
	// updatedVals.add(vmSel);
	//
	// return updatedVals;
	// }
	//
	// private VirtualMachine getVMWithMaxUnitCost(ArrayList<VirtualMachine> vms) {
	// Collections.sort(vms, new Comparator<VirtualMachine>() {
	// @Override
	// public int compare(VirtualMachine v1, VirtualMachine v2) {
	// return Double.compare(v1.getUnit_cost_vm(), v2.getUnit_cost_vm());
	// }
	// });
	//
	// return vms.get(vms.size() - 1);
	// }

}
