package com.tool.collabhub.utils;

import com.tool.collabhub.elasticsearch.service.ProjectIndexService;
import com.tool.collabhub.model.Project;
import com.tool.collabhub.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectDataGenerator {

    private final ProjectRepository projectRepository;
    private final ProjectIndexService projectIndexService;

    private static final List<String> TECH_STACK_POOL = List.of(
            "Java", "Spring Boot", "MySQL", "MongoDB", "React", "Angular",
            "Node.js", "Docker", "Kubernetes", "Redis", "Elasticsearch", "AWS",
            "PostgreSQL", "Python", "FastAPI", "Next.js", "TypeScript", "Firebase",
            "GraphQL", "RabbitMQ", "Azure", "Supabase", "TailwindCSS", "GitHub Actions"
    );

    private static final List<String> TITLE_KEYWORDS = List.of(
            "AI Assistant", "E-Commerce Platform", "Inventory Manager", "Analytics Dashboard",
            "Job Portal", "Learning Management System", "Collaboration Tool", "Social Network",
            "Streaming App", "Health Tracker", "Expense Monitor", "Chat Application",
            "Remote Work Platform", "Online Exam System", "Smart Home Controller", "Crypto Wallet",
            "Real-Time Chat", "News Aggregator", "Budget Planner", "Marketplace Builder",
            "Fitness Tracker", "Language Tutor", "Travel Planner", "Resume Builder",
            "Online Food Ordering System", "Digital Library", "Appointment Scheduler",
            "Event Management Tool", "Virtual Whiteboard", "E-voting System", "Crowdfunding Platform",
            "Freelance Marketplace", "Online Auction Site", "Customer Support Bot", "Code Sharing Tool",
            "Habit Tracker", "Online Banking Portal", "Content Recommendation Engine",
            "Subscription Billing Platform", "Portfolio Website Generator", "Online Quiz Builder",
            "Book Review Platform", "IoT Device Dashboard", "Project Management Tool", "Voice Controlled App",
            "Online Rental Service", "Feedback Collection System", "Daily Planner", "Resume Analyzer",
            "Peer Learning Community", "Team Retrospective Tool", "Voice Call API Platform"
    );


    private static final List<String> DESCRIPTION_TEMPLATES = List.of(
            "A robust %s built with %s for scalable performance.",
            "An intuitive platform for managing %s using %s.",
            "A modern application enabling %s features powered by %s.",
            "End-to-end solution for %s, leveraging %s.",
            "Smart system for streamlining %s operations with %s integration.",
            "Custom-built %s powered by cutting-edge %s technology.",
            "Lightweight and responsive %s solution utilizing %s.",
            "Reliable backend and interactive UI for %s created with %s.",
            "Full-stack %s offering seamless experience via %s.",
            "Secure and scalable architecture designed for %s using %s.",
            "Designed to revolutionize %s management using %s.",
            "A user-friendly solution for %s with intelligent %s features.",
            "Cloud-native %s application developed using %s.",
            "Enterprise-grade platform to handle %s with help from %s.",
            "Minimalistic and mobile-first %s tool built on %s.",
            "Open-source project aiming to simplify %s using %s.",
            "Optimized for performance and usability in %s scenarios via %s.",
            "Seamlessly integrates with modern stacks for %s through %s.",
            "Automates and enhances %s workflows using %s technology.",
            "Built for collaboration in %s environments using %s.",
            "Cross-platform %s application designed with %s.",
            "Efficient architecture targeting %s problems using %s.",
            "Modular and extensible %s platform, relying on %s.",
            "Production-ready implementation of %s utilizing %s.",
            "High-availability service to manage %s operations with %s."
    );

    private static final Random RANDOM = new Random();

    private final Set<String> usedTitles = new HashSet<>();
    private final Set<String> usedDescriptions = new HashSet<>();

    public void generateProjects(List<String> userIds, int projectsPerUser) {

        List<Project> projects = new ArrayList<>();

        for (String userId : userIds) {
            for (int i = 0; i < projectsPerUser; i++) {
                projects.add(generateSingleProject(userId));
            }
        }

        projectRepository.saveAll(projects);
        log.info("Saved : {} in projects db", projects.size());

        for(Project project : projects){
            projectIndexService.save(project);
        }

        log.info("Saved : {} in projects index db", projects.size());

    }

    private Project generateSingleProject(String userId) {
        Project project = new Project();

        List<String> techStack = getRandomSubset(TECH_STACK_POOL, 3 + RANDOM.nextInt(3)); // 3–5 items
        String keyword = getRandom(TITLE_KEYWORDS);
        String title = null;
        String description = null;

        int maxAttempts = 10;
        int attempt = 0;

        // Try to generate a unique title
        while (attempt < maxAttempts) {
            title = keyword + " using " + techStack.get(0);
            if (usedTitles.add(title)) break;
            keyword = getRandom(TITLE_KEYWORDS); // Try different keyword
            attempt++;
        }

        if (attempt == maxAttempts) {
            // Force insert even if duplicate (or append a random UUID)
            title = title + " " + UUID.randomUUID().toString().substring(0, 5);
            usedTitles.add(title);
        }

        // Reset for description
        attempt = 0;

        while (attempt < maxAttempts) {
            description = String.format(
                    getRandom(DESCRIPTION_TEMPLATES),
                    keyword,
                    String.join(", ", techStack)
            );
            if (usedDescriptions.add(description)) break;
            keyword = getRandom(TITLE_KEYWORDS);
            attempt++;
        }

        if (attempt == maxAttempts) {
            description = description + " (v" + RANDOM.nextInt(100) + ")";
            usedDescriptions.add(description);
        }

        project.setId(UUID.randomUUID().toString());
        project.setTitle(title);
        project.setDescription(description);
        project.setTechStack(techStack);
        project.setUserId(userId);

        LocalDateTime now = LocalDateTime.now().minusDays(RANDOM.nextInt(90));
        project.setCreatedOn(now);
        project.setCreatedBy(userId);
        project.setUpdatedOn(now.plusDays(RANDOM.nextInt(30)));
        project.setUpdatedBy(userId);

        return project;
    }

    private static <T> T getRandom(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    private static List<String> getRandomSubset(List<String> list, int count) {
        List<String> shuffled = new ArrayList<>(list);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, count);
    }
}