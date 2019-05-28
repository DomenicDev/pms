package de.hfu.pms.pool;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.SuccessfullyAddedUniversityEvent;
import de.hfu.pms.service.ApplicationServices;
import de.hfu.pms.shared.dto.PreviewDoctoralStudentDTO;
import de.hfu.pms.shared.dto.UniversityDTO;
import de.hfu.pms.shared.dto.UserDTO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;

public final class EntityPool {

    private static EntityPool entityPool = new EntityPool();

    private EventBus eventBus = EventBusSystem.getEventBus();
    private static Logger logger = Logger.getLogger(EntityPool.class);
    private Collection<UniversityDTO> universities = new HashSet<>();
    private Collection<PreviewDoctoralStudentDTO> previewStudents = new HashSet<>();
    private Collection<UserDTO> users = new HashSet<>();
    private ApplicationServices applicationServices;

    private EntityPool() {
        // private constructor
    }

    public static EntityPool getInstance() {
        return entityPool;
    }

    public void setApplicationServices(ApplicationServices applicationServices) {
        this.applicationServices = applicationServices;
    }

    public ApplicationServices getApplicationServices() {
        return applicationServices;
    }

    public void addAll(Collection<UniversityDTO> data) {
        if (data == null) {
            return;
        }
        logger.log(Level.DEBUG, "Adding " + data.size() + " items to university pool.");
        universities.addAll(data);
    }

    public void initPreviews(Collection<PreviewDoctoralStudentDTO> previews) {
        if (previews == null) {
            return;
        }
        this.previewStudents.addAll(previews);
    }

    public void initUsers(Collection<UserDTO> initUsers){
        if (initUsers == null){
            return;
        }
        logger.log(Level.DEBUG, "Adding " + initUsers.size() + " items to users pool.");
        users.addAll(initUsers);
    }

    public Collection<UniversityDTO> getUniversities() {
        return universities;
    }

    public Collection<PreviewDoctoralStudentDTO> getPreviewStudents() {
        return previewStudents;
    }

    public Collection<UserDTO> getUsers() {
        return users;
    }

    @Subscribe
    public void handleCreateUniversityEvent(SuccessfullyAddedUniversityEvent event) {
        universities.add(event.getUniversity());
    }
}
