package com.arom.jobzi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arom.jobzi.R;
<<<<<<< HEAD
import com.arom.jobzi.ServiceEditorActivity;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.util.Util;

public class ServiceProviderFragment extends Fragment implements ServiceListFragment.ServiceItemListener, DeleteServiceDialogFragment.DeleteServiceListener {

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

	    View view = inflater.inflate(R.layout.fragment_service_provider, container, false);
=======
import com.arom.jobzi.ServiceSelectorActivity;
import com.arom.jobzi.fragment.serviceprovider.ServiceProviderServicesFragment;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
>>>>>>> 202544867ef7354e4575607bde558b7471b33ad6

public class ServiceProviderFragment extends Fragment implements ServiceProviderServicesFragment.ServiceItemListener, DeleteServiceDialogFragment.DeleteServiceListener {
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_service_provider, container, false);
        
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager());
        
        AvailableTimeSlotsListFragment availableTimeSlotsListFragment = new AvailableTimeSlotsListFragment();
        ServiceProviderServicesFragment serviceListFragment = ServiceProviderServicesFragment.newInstance(this);
        
        customPagerAdapter.addFragment(availableTimeSlotsListFragment, getText(R.string.availabilities_label));
        customPagerAdapter.addFragment(serviceListFragment, getText(R.string.services_label));
        
        viewPager.setAdapter(customPagerAdapter);
        
        return view;
        
    }
<<<<<<< HEAD


    @Override
    public void onDelete(Service service) {
        Util.getInstance().deleteService(service);
        Util.getInstance().updateUser();
	}


    @Override
    public void onClick(Service service) {
	    }

    @Override
    public void onLongClick(Service service) {
=======
    
    @Override
    public void onDelete(final Service service) {
        
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference userProfileDatabase = Util.getInstance().getProfilesDatabase().child(user.getUid());
        
        userProfileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                profile.getServices().remove(service);
                userProfileDatabase.setValue(profile);
                
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
        
            }
        });
        
    }
    
    @Override
    public void requestRemoveService(Service service) {
        
>>>>>>> 202544867ef7354e4575607bde558b7471b33ad6
        DeleteServiceDialogFragment deleteServiceDialogFragment = new DeleteServiceDialogFragment();
        
        Bundle bundle = new Bundle();
        bundle.putSerializable(DeleteServiceDialogFragment.LISTENER_BUNDLE_ARG, this);
        bundle.putSerializable(DeleteServiceDialogFragment.SERVICE_BUNDLE_ARG, service);
        
        deleteServiceDialogFragment.setArguments(bundle);
        
        deleteServiceDialogFragment.show(this.getActivity().getSupportFragmentManager(), "");
<<<<<<< HEAD


=======
        
    }
    
    @Override
    public void requestAddService() {
        
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference profilesDatabase = Util.getInstance().getProfilesDatabase();
        
        profilesDatabase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                
                Intent toServiceSelectorIntent = new Intent(getActivity(), ServiceSelectorActivity.class);
                
                Bundle bundle = new Bundle();
                
                if(profile.getServices() != null) {
                    bundle.putParcelableArrayList(ServiceSelectorActivity.SERVICES_TO_EXCLUDE_BUNDLE_ARG, (ArrayList<Service>) profile.getServices());
                }
                
                toServiceSelectorIntent.putExtras(bundle);
                
                startActivityForResult(toServiceSelectorIntent, ServiceSelectorActivity.SERVICE_SELECTED_RESULT);
                
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });
        
    }
    
    @Override
    public void onServiceDeleted(Service service) {
        onDelete(service);
>>>>>>> 202544867ef7354e4575607bde558b7471b33ad6
    }
    
    @Override
<<<<<<< HEAD
    public void addService() {
        Service service = new Service();

        service.setName("");
        service.setRate(0);


=======
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        switch (resultCode) {
            case ServiceSelectorActivity.SERVICE_SELECTED_RESULT:
                
                final Service selectedService = (Service) data.getSerializableExtra(ServiceSelectorActivity.SERVICE_SELECTED_BUNDLE_ARG);
                
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference userProfileDatabase = Util.getInstance().getProfilesDatabase().child(user.getUid());
                userProfileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        
                        ServiceProviderProfile profile = dataSnapshot.getValue(ServiceProviderProfile.class);
                        
                        if(profile.getServices() == null) {
                            profile.setServices(new ArrayList<Service>());
                        }
                        
                        profile.getServices().add(selectedService);
                        
                        userProfileDatabase.setValue(profile);
                        
                    }
    
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
        
                    }
                });
                
                break;
            case ServiceSelectorActivity.NO_SERVICES_FOUND_RESULT:
                Toast.makeText(this.getContext(), "No services that you can add were found.", Toast.LENGTH_LONG).show();
                break;
            case ServiceSelectorActivity.CANCEL_RESULT:
                // No need to do anything.
                break;
        }
>>>>>>> 202544867ef7354e4575607bde558b7471b33ad6
    }
}
