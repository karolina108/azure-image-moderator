package pro.koliber.azure.moderator.image.service;

import pro.koliber.azure.moderator.image.model.ComputerVisionResult;
import pro.koliber.azure.moderator.image.model.ModerationResult;
import pro.koliber.azure.moderator.image.model.ModerationStatus;
import pro.koliber.azure.moderator.image.model.Tag;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ModerationService {

    private static final List<String> ART_CONTENT_TAGS = Arrays.asList("art", "sculpture", "figure", "painting", "statue");
    private static final List<String> DISALLOWED_CONTENT_TAGS = Arrays.asList("weapon", "gun", "violence");

    public ModerationResult moderateImage(ComputerVisionResult result){

        ModerationResult moderationResult = new ModerationResult();
        moderationResult.setAdultInfo(result.getAdult());
        moderationResult.setTags(result.getTags());

        List<Tag> tags = result.getTags();
        Boolean isAdultContent = result.getAdult().getAdultContent();
        Boolean isRacyContent = result.getAdult().getRacyContent();

        if(isAdultContent || isRacyContent){

            ModerationStatus status = checkArtCondition(tags);
            moderationResult.setStatus(status);

        } else {

            ModerationStatus status = checkDisallowedConent(tags);
            moderationResult.setStatus(status);
        }

        return moderationResult;
    }

    private ModerationStatus checkDisallowedConent(List<Tag> tags) {

        Comparator<Tag> tagComparator = Comparator.comparing(Tag::getConfidence);

        Optional<Tag> disallowed = tags.stream()
                .filter(tag -> DISALLOWED_CONTENT_TAGS.contains(tag.getName()))
                .max(tagComparator);

        if(disallowed.isPresent()) {

            Double confidence = disallowed.get().getConfidence();
            System.out.println("Dissallowed confidence " + String.valueOf(confidence));

            if (confidence > 0.5d){
                return ModerationStatus.REJECTED;

            } else {
                return ModerationStatus.FOR_REVIEW;
            }
        }

        return ModerationStatus.ACCEPTED;
    }

    private ModerationStatus checkArtCondition(List<Tag> tags) {

        Comparator<Tag> tagComparator = Comparator.comparing(Tag::getConfidence);

        Optional<Tag> artTag = tags.stream()
                .filter(tag -> ART_CONTENT_TAGS.contains(tag.getName()))
                .max(tagComparator);

        if(artTag.isPresent()){

            Double artConfidence = artTag.get().getConfidence();

            if (artConfidence > 0.5){
                return ModerationStatus.ACCEPTED;

            } else {
                return ModerationStatus.FOR_REVIEW;
            }

        } else {
            return ModerationStatus.REJECTED;
        }

    }


}
