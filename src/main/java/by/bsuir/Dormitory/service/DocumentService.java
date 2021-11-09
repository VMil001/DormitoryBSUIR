package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.DocumentRequest;
import by.bsuir.Dormitory.dto.response.DocumentResponse;
import by.bsuir.Dormitory.exception.DocumentNotFoundException;
import by.bsuir.Dormitory.mapper.DocumentMapper;
import by.bsuir.Dormitory.model.Document;
import by.bsuir.Dormitory.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;

    @Transactional(readOnly = true)
    public List<DocumentResponse> getAll() {
        return documentRepository.findAll()
                .stream()
                .map(documentMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public DocumentResponse getById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException(id));
        return documentMapper.mapToDto(document);
    }

    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }

    public void save(DocumentRequest documentRequest) {
        Document document = documentMapper.map(documentRequest);
        documentRepository.save(document);
    }
}
